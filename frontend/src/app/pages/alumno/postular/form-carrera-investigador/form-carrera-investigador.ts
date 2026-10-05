import { CommonModule } from '@angular/common';
import { Component, inject, input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LabelEnumPipe } from '../../../../pipes/label-enum-pipe';
import { Router } from '@angular/router';
import { PerfilService } from '../../../../services/perfil-service';
import { PostulacionService } from '../../../../services/postulacion-service';
import { ArchivoService } from '../../../../services/archivo-service';
import { Genero } from '../../../../models/genero';
import { TipoDocumento } from '../../../../models/tipo-documento';
import { CarreraGrado } from '../../../../models/carrera-grado';
import { CategoriaInvestigador, CategoriaInvestigadorDescripcion } from '../../../../models/categoria-investigador';
import { PostulacionCarreraInvestigadorRequest } from '../../../../models/postulacion-carrera-investigador-request';
import { UbicacionService } from '../../../../services/ubicacion-service';
import { firstValueFrom } from 'rxjs';

const MAX_MB_POR_ARCHIVO = 5;

@Component({
  selector: 'app-form-carrera-investigador',
  imports: [ReactiveFormsModule, CommonModule, LabelEnumPipe],
  templateUrl: './form-carrera-investigador.html',
  styleUrl: './form-carrera-investigador.css',
})
export class FormCarreraInvestigador implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private perfilService = inject(PerfilService);
  private ubicacionService = inject(UbicacionService);
  private postulacionService = inject(PostulacionService);
  private archivoService = inject(ArchivoService);

  private nacionalidadCodigo = '';
  private provinciaCodigo = '';
  private localidadCodigo = '';

  readonly convocatoriaId = input.required<number>();

  form!: FormGroup;
  cargandoDatos = signal(true);
  enviando = false;
  intentoEnvio = false;
  erroresArchivo: string[] = [];

  tipoDocumento = TipoDocumento;
  archivos: Partial<Record<TipoDocumento, File[]>> = {};

  // Enums
  generos = Object.values(Genero);
  categoriasInvestigador = Object.values(CategoriaInvestigador);
  carrerasGrado = Object.values(CarreraGrado);
  categoriaInvestigadorDescripcion = CategoriaInvestigadorDescripcion;

  ngOnInit(): void {
    this.inicializarForm();
    this.cargarDatosPersonales();
  }

  inicializarForm(): void {
    this.form = this.fb.group({
      // Datos personales
      dpNombre:             [{ value: '', disabled: true }],
      dpApellido:           [{ value: '', disabled: true }],
      dpDni:                ['', Validators.required],
      dpFechaNacimiento:    ['', Validators.required],
      dpGenero:             [null as Genero | null, Validators.required],
      dpCelular:            ['', [Validators.required, Validators.pattern('^[1-9][0-9]{9,10}$')]],
      dpDomicilioCalle:     ['', Validators.required],
      dpDomicilioNumero:    ['', Validators.required],
      dpDomicilioPisoDepto: [''],
      dpCodigoPostal:       ['', Validators.required],
      dpLocalidad:          ['', Validators.required],
      dpProvincia:          ['', Validators.required],
      dpNacionalidad:       ['', Validators.required],

      // Datos CarreraInvestigador
      categoriaActual:     [null as CategoriaInvestigador | null],
      categoriaSolicitada: [null as CategoriaInvestigador | null, Validators.required],
      carreraGrado:        [null as CarreraGrado | null, Validators.required],
      materia:             ['', Validators.required],

      // Declaración
      aceptaDeclaracion: [false, Validators.requiredTrue],
    });
  }

  async cargarDatosPersonales(): Promise<void> {
    this.perfilService.obtenerMisDatos().subscribe({
      next: async (datos) => {
        this.nacionalidadCodigo = datos.nacionalidad;
        this.provinciaCodigo = datos.provincia;
        this.localidadCodigo = datos.localidad;

        const nacionalidades = await this.ubicacionService.getNacionalidades();
        const provincias = await this.ubicacionService.getProvincias();

        const nacionalidad = nacionalidades.find(n => n.iso2 === datos.nacionalidad);
        const provincia = provincias.find(p => p.iso2 === datos.provincia);

        let localidadNombre = '';
        if (datos.provincia && datos.localidad) {
          const localidades = await this.ubicacionService.getLocalidades(datos.provincia);
          const localidad = localidades.find(l => String(l.id) === String(datos.localidad));
          localidadNombre = localidad?.name ?? String(datos.localidad);
        }

        this.form.patchValue({
          dpDni:                datos.dni,
          dpFechaNacimiento:    datos.fechaNacimiento,
          dpGenero:             datos.genero,
          dpCelular:            datos.celular,
          dpDomicilioCalle:     datos.domicilioCalle,
          dpDomicilioNumero:    datos.domicilioNumero,
          dpDomicilioPisoDepto: datos.domicilioPisoDepto,
          dpCodigoPostal:       datos.codigoPostal,
          dpLocalidad:          localidadNombre,
          dpProvincia:          provincia?.name ?? datos.provincia,
          dpNacionalidad:       nacionalidad?.name ?? datos.nacionalidad,
        });

        this.cargandoDatos.set(false);
      },
      error: (error) => {
        console.error('Error al obtener los datos personales:', error);
        this.cargandoDatos.set(false);
      },
    });
  }

  onArchivoSeleccionado(event: Event, tipo: TipoDocumento): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const archivosArray = Array.from(input.files);
      const invalidos = archivosArray.filter(f => f.size > MAX_MB_POR_ARCHIVO * 1024 * 1024);
      if (invalidos.length > 0) {
        this.erroresArchivo.push(`El archivo "${invalidos[0].name}" supera el tamaño máximo de ${MAX_MB_POR_ARCHIVO} MB.`);
        return;
      }
      this.archivos[tipo] = archivosArray;
      this.erroresArchivo = [];
    }
  }

  campoInvalido(campo: string): boolean {
    const control = this.form.get(campo);
    return !!(control && control.invalid && (control.dirty || control.touched || this.intentoEnvio));
  }

  async onSubmit(): Promise<void> {
    this.intentoEnvio = true;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const obligatorios = [TipoDocumento.FORMULARIO_INSCRIPCION, TipoDocumento.CURRICULUM_VITAE];
    const faltantes = obligatorios.filter(tipo => !this.archivos[tipo]?.length);
    if (faltantes.length > 0) {
      this.erroresArchivo.push('Faltan adjuntar documentos obligatorios.');
      return;
    }

    this.enviando = true;
    const v = this.form.getRawValue();

    const body: PostulacionCarreraInvestigadorRequest = {
      convocatoriaId: this.convocatoriaId(),
      datosPersonales: {
        dni:                       v.dpDni,
        fechaNacimiento:           v.dpFechaNacimiento,
        genero:                    v.dpGenero,
        celular:                   v.dpCelular,
        domicilioCalle:            v.dpDomicilioCalle,
        domicilioNumero:           v.dpDomicilioNumero,
        domicilioPisoDepto:        v.dpDomicilioPisoDepto || null,
        codigoPostal:              v.dpCodigoPostal,
        localidad:                 this.localidadCodigo,
        provincia:                 this.provinciaCodigo,
        nacionalidad:              this.nacionalidadCodigo,
        domicilioFamiliarDistinto: false,
      },
      categoriaActual:     v.categoriaActual || null,
      categoriaSolicitada: v.categoriaSolicitada,
      carreraGrado:        v.carreraGrado,
      materia:             v.materia,
    };

    try {
      const postulacion = await firstValueFrom(this.postulacionService.postularCarreraInvestigador(body));
      await this.subirArchivos(postulacion.id);
      await firstValueFrom(this.postulacionService.finalizar(postulacion.id));
      this.enviando = false;
      this.router.navigate(['/mis-postulaciones']);
    } catch (err) {
      this.enviando = false;
      console.error('Error al postularse:', err);
    }
  }

  private async subirArchivos(postulacionId: number): Promise<void> {
    const uploads: Promise<unknown>[] = [];
    (Object.entries(this.archivos) as [TipoDocumento, File[]][]).forEach(([tipo, archivosArray]) => {
      archivosArray.forEach(archivo => {
        const formData = new FormData();
        formData.append('file', archivo);
        formData.append('tipoArchivo', tipo);
        uploads.push(firstValueFrom(this.archivoService.subir(postulacionId, formData)));
      });
    });
    await Promise.all(uploads);
  }
}
