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
import { AuthService } from '../../../../services/auth-service';
import { ActivatedRoute } from '@angular/router';

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
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);

  convocatoriaId!: number;

  //Listas de ubicacion
  nacionalidades = signal<any[]>([]);
  provincias = signal<any[]>([]);
  localidades = signal<any[]>([]);

  provinciasFamiliar = signal<any[]>([]);
  localidadesFamiliar = signal<any[]>([]);

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
    this.convocatoriaId = Number(this.route.snapshot.paramMap.get('convocatoriaId'));
    this.inicializarForm();
    this.cargarNombreUsuario();
    this.configurarValidacionesCondicionales();
    this.cargarDatosPersonales();
  }

  private cargarNombreUsuario(): void {
    const usuario = this.authService.getCurrentUser();
    this.form.patchValue({
      dpNombre: usuario?.nombre ?? '',
      dpApellido: usuario?.apellido ?? '',
    });
  }

  inicializarForm(): void {
    this.form = this.fb.group({
      // Datos personales
      dpNombre: [{ value: '', disabled: true }],
      dpApellido: [{ value: '', disabled: true }],
      dpDni: ['', [Validators.required, Validators.pattern(/^[A-Za-z0-9]{6,15}$/)]],
      dpFechaNacimiento: ['', Validators.required],
      dpGenero: [null as Genero | null, Validators.required],
      dpCelular: ['', [Validators.required, Validators.pattern('^[1-9][0-9]{9,10}$')]],
      dpDomicilioCalle: ['', Validators.required],
      dpDomicilioNumero: ['', Validators.required],
      dpDomicilioPisoDepto: [''],
      dpCodigoPostal: ['', Validators.required],
      dpLocalidad: ['', Validators.required],
      dpProvincia: ['', Validators.required],
      dpNacionalidad: ['', Validators.required],
      dpCbuCvu: ['', [Validators.required, Validators.pattern(/^\d{22}$/)]],

      // Domicilio familiar (opcional, se valida solo si el checkbox está tildado)
      domicilioFamiliarDistinto: [false],
      domicilioFamiliarCalle: [''],
      domicilioFamiliarNumero: [''],
      domicilioFamiliarPisoDepto: [''],
      domicilioFamiliarCodigoPostal: [''],
      domicilioFamiliarProvincia: [''],
      domicilioFamiliarLocalidad: [''],

      // Datos CarreraInvestigador
      categoriaActual: [null as CategoriaInvestigador | null],
      categoriaSolicitada: [null as CategoriaInvestigador | null, Validators.required],
      carreraGrado: [null as CarreraGrado | null, Validators.required],
      materia: ['', Validators.required],

      // Declaración
      aceptaDeclaracion: [false, Validators.requiredTrue],
    });
  }

  async cargarDatosPersonales(): Promise<void> {
    // 1. Listas: si fallan, no bloquean el resto del formulario
    try {
      this.nacionalidades.set(await this.ubicacionService.getNacionalidades());
      this.provincias.set(await this.ubicacionService.getProvincias());
      this.provinciasFamiliar.set(this.provincias());
    } catch (err) {
      console.error('Error al cargar las listas de ubicación:', err);
    }

    // 2. Perfil
    this.perfilService.obtenerMisDatos().subscribe({
      next: async (datos) => {
        try {
          if (datos.provincia) {
            this.localidades.set(await this.ubicacionService.getLocalidades(datos.provincia));
          }
          if (datos.domicilioFamiliarProvincia) {
            this.localidadesFamiliar.set(
              await this.ubicacionService.getLocalidades(datos.domicilioFamiliarProvincia));
          }
        } catch (err) {
          console.error('Error al cargar las localidades:', err);
        }

        this.form.patchValue({
          dpDni: datos.dni,
          dpFechaNacimiento: datos.fechaNacimiento,
          dpGenero: datos.genero,
          dpCelular: datos.celular,
          dpDomicilioCalle: datos.domicilioCalle,
          dpDomicilioNumero: datos.domicilioNumero,
          dpDomicilioPisoDepto: datos.domicilioPisoDepto,
          dpCodigoPostal: datos.codigoPostal,
          dpLocalidad: datos.localidad,
          dpProvincia: datos.provincia,
          dpNacionalidad: datos.nacionalidad,
          dpCbuCvu: datos.cbuCvu,

          domicilioFamiliarDistinto: datos.domicilioFamiliarDistinto,
          domicilioFamiliarCalle: datos.domicilioFamiliarCalle,
          domicilioFamiliarNumero: datos.domicilioFamiliarNumero,
          domicilioFamiliarPisoDepto: datos.domicilioFamiliarPisoDepto,
          domicilioFamiliarCodigoPostal: datos.domicilioFamiliarCodigoPostal,
          domicilioFamiliarProvincia: datos.domicilioFamiliarProvincia,
          domicilioFamiliarLocalidad: datos.domicilioFamiliarLocalidad,
        });

        this.cargandoDatos.set(false);
      },
      error: (error) => {
        if (error.status !== 404) {
          console.error('Error al obtener los datos personales:', error);
        }
        this.cargandoDatos.set(false);
      },
    });
  }

  async onProvinciaChange(codigoProvincia: string): Promise<void> {
    this.localidades.set([]);
    this.form.patchValue({ dpLocalidad: '' });
    if (codigoProvincia) {
      this.localidades.set(await this.ubicacionService.getLocalidades(codigoProvincia));
    }
  }

  async cargarLocalidadesFamiliar(codigoProvincia: string): Promise<void> {
    this.localidadesFamiliar.set([]);
    this.form.patchValue({ domicilioFamiliarLocalidad: '' });
    if (codigoProvincia) {
      this.localidadesFamiliar.set(await this.ubicacionService.getLocalidades(codigoProvincia));
    }
  }

  configurarValidacionesCondicionales(): void {
    const distinto = this.form.get('domicilioFamiliarDistinto');

    const camposObligatorios = [
      'domicilioFamiliarCalle',
      'domicilioFamiliarNumero',
      'domicilioFamiliarCodigoPostal',
      'domicilioFamiliarProvincia',
      'domicilioFamiliarLocalidad',
    ];

    distinto?.valueChanges.subscribe((valor: boolean) => {
      camposObligatorios.forEach((campo) => {
        const control = this.form.get(campo);
        if (valor) {
          control?.setValidators(Validators.required);
        } else {
          control?.clearValidators();
          control?.setValue('');
        }
        control?.updateValueAndValidity();
      });

      if (!valor) {
        this.form.get('domicilioFamiliarPisoDepto')?.setValue('');
        this.localidadesFamiliar.set([]);
      }
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
      convocatoriaId: this.convocatoriaId,
      datosPersonales: {
        dni: v.dpDni,
        fechaNacimiento: v.dpFechaNacimiento,
        genero: v.dpGenero,
        celular: v.dpCelular,
        domicilioCalle: v.dpDomicilioCalle,
        domicilioNumero: v.dpDomicilioNumero,
        domicilioPisoDepto: v.dpDomicilioPisoDepto || null,
        codigoPostal: v.dpCodigoPostal,
        localidad: v.dpLocalidad,
        provincia: v.dpProvincia,
        nacionalidad: v.dpNacionalidad,
        cbuCvu: v.dpCbuCvu,
        domicilioFamiliarDistinto: v.domicilioFamiliarDistinto,
        domicilioFamiliarCalle: v.domicilioFamiliarDistinto ? v.domicilioFamiliarCalle : null,
        domicilioFamiliarNumero: v.domicilioFamiliarDistinto ? v.domicilioFamiliarNumero : null,
        domicilioFamiliarPisoDepto: v.domicilioFamiliarDistinto ? (v.domicilioFamiliarPisoDepto || null) : null,
        domicilioFamiliarCodigoPostal: v.domicilioFamiliarDistinto ? v.domicilioFamiliarCodigoPostal : null,
        domicilioFamiliarLocalidad: v.domicilioFamiliarDistinto ? v.domicilioFamiliarLocalidad : null,
        domicilioFamiliarProvincia: v.domicilioFamiliarDistinto ? v.domicilioFamiliarProvincia : null,

      },
      categoriaActual: v.categoriaActual || null,
      categoriaSolicitada: v.categoriaSolicitada,
      carreraGrado: v.carreraGrado,
      materia: v.materia,
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
