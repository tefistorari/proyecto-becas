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
import { CategoriaBinid } from '../../../../models/categoria-binid';
import { firstValueFrom } from 'rxjs';
import { PostulacionBinidRequest } from '../../../../models/postulacion-binid-request';
import { UbicacionService } from '../../../../services/ubicacion-service';
import { AuthService } from '../../../../services/auth-service';

const MAX_MB_POR_ARCHIVO = 5;
@Component({
  selector: 'app-formulario-binid',
  imports: [ReactiveFormsModule, CommonModule, LabelEnumPipe],
  templateUrl: './formulario-binid.html',
  styleUrl: './formulario-binid.css',
})
export class FormularioBinid implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private perfilService = inject(PerfilService);
  private ubicacionService = inject(UbicacionService);
  private postulacionService = inject(PostulacionService);
  private archivoService = inject(ArchivoService);
  private authService = inject(AuthService);

  readonly convocatoriaId = input.required<number>();

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

  generos = Object.values(Genero);
  anioActual = new Date().getFullYear();

  //Claves internas en minuscula, tal como las usa el html
  archivos: Record<string, File[]> = {};

  // Clave interna (html) -> TipoDocumento real que espera el backend
  private readonly mapaClaveATipo: Record<string, TipoDocumento> = {
    dni: TipoDocumento.DNI,
    nota_aval: TipoDocumento.NOTA_AVAL_DIRECTOR,
    carta_motivacion: TipoDocumento.CARTA_MOTIVACION,
    analitico: TipoDocumento.ANALITICO,
    certificado_alumno: TipoDocumento.CERTIFICADO_ALUMNO_REGULAR,
  };

  // Valor que manda el (change) del input file -> clave interna
  private readonly mapaTipoAClave: Record<string, string> = {
    DNI: 'dni',
    NOTA_AVAL_DIRECTOR: 'nota_aval',
    CARTA_MOTIVACION: 'carta_motivacion',
    ANALITICO: 'analitico',
    CERTIFICADO_ALUMNO_REGULAR: 'certificado_alumno',
  };

  ngOnInit(): void {
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
      dpDni: ['', Validators.required],
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

      // Domicilio familiar (opcional, se valida solo si el checkbox está tildado)
      domicilioFamiliarDistinto: [false],
      domicilioFamiliarCalle: [''],
      domicilioFamiliarNumero: [''],
      domicilioFamiliarPisoDepto: [''],
      domicilioFamiliarCodigoPostal: [''],
      domicilioFamiliarProvincia: [''],
      domicilioFamiliarLocalidad: [''],

      // Información académica
      categoriaBinid: [null as CategoriaBinid | null, Validators.required],
      carreraGrado: ['', Validators.required],
      anioIngreso: ['', Validators.required],
      anioEgreso: [''],          // required condicional (egresado/graduado)
      materiasCursadas: [''],    // required condicional (estudiante avanzado)
      promedioConAplazos: ['', Validators.required],
      promedioSinAplazos: [''],
      accedioABecaBinidAnterior: [null as boolean | null, Validators.required],

      // Datos del proyecto
      nombreDirectorProyecto: ['', Validators.required],
      apellidoDirectorProyecto: ['', Validators.required],
      pregunta: ['', Validators.required],

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

  // --- Campos condicionales según categoría BINID ---

  get esGraduadoOEgresado(): boolean {
    const cat = this.form?.get('categoriaBinid')?.value;
    return cat === CategoriaBinid.GRADUADO || cat === CategoriaBinid.EGRESADO;
  }

  get esEstudianteAvanzado(): boolean {
    return this.form?.get('categoriaBinid')?.value === CategoriaBinid.ESTUDIANTE_AVANZADO;
  }

  private configurarValidacionesCondicionales(): void {
    const anioEgreso = this.form.get('anioEgreso')!;
    const materiasCursadas = this.form.get('materiasCursadas')!;

    this.form.get('categoriaBinid')!.valueChanges.subscribe((categoria: CategoriaBinid) => {
      if (categoria === CategoriaBinid.GRADUADO || categoria === CategoriaBinid.EGRESADO) {
        anioEgreso.setValidators(Validators.required);
        materiasCursadas.clearValidators();
        materiasCursadas.setValue('');
      } else if (categoria === CategoriaBinid.ESTUDIANTE_AVANZADO) {
        materiasCursadas.setValidators(Validators.required);
        anioEgreso.clearValidators();
        anioEgreso.setValue('');
      } else {
        anioEgreso.clearValidators();
        materiasCursadas.clearValidators();
      }
      anioEgreso.updateValueAndValidity();
      materiasCursadas.updateValueAndValidity();
    });

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

  // --- Archivos ---

  onArchivoSeleccionado(event: Event, tipo: string): void {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const archivosArray = Array.from(input.files);
    const invalidos = archivosArray.filter((f) => f.size > MAX_MB_POR_ARCHIVO * 1024 * 1024);
    if (invalidos.length > 0) {
      this.erroresArchivo.push(
        `El archivo "${invalidos[0].name}" supera el tamaño máximo de ${MAX_MB_POR_ARCHIVO} MB.`
      );
      return;
    }

    const clave = this.mapaTipoAClave[tipo] ?? tipo.toLowerCase();
    this.archivos[clave] = archivosArray;
    this.erroresArchivo = [];
  }

  // --- Validación de campos ---

  campoInvalido(campo: string): boolean {
    const control = this.form.get(campo);
    return !!(control && control.invalid && (control.dirty || control.touched || this.intentoEnvio));
  }

  // --- Submit ---

  async onSubmit(): Promise<void> {
    this.intentoEnvio = true;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const obligatorios = ['dni', 'nota_aval', 'carta_motivacion', 'analitico', 'certificado_alumno'];
    const faltantes = obligatorios.filter((clave) => !this.archivos[clave]?.length);
    if (faltantes.length > 0) {
      this.erroresArchivo.push('Faltan adjuntar documentos obligatorios.');
      return;
    }

    this.enviando = true;
    const v = this.form.getRawValue();

    const body: PostulacionBinidRequest = {
      convocatoriaId: this.convocatoriaId(),
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
        domicilioFamiliarDistinto: v.domicilioFamiliarDistinto,
        domicilioFamiliarCalle: v.domicilioFamiliarDistinto ? v.domicilioFamiliarCalle : null,
        domicilioFamiliarNumero: v.domicilioFamiliarDistinto ? v.domicilioFamiliarNumero : null,
        domicilioFamiliarPisoDepto: v.domicilioFamiliarDistinto ? (v.domicilioFamiliarPisoDepto || null) : null,
        domicilioFamiliarCodigoPostal: v.domicilioFamiliarDistinto ? v.domicilioFamiliarCodigoPostal : null,
        domicilioFamiliarLocalidad: v.domicilioFamiliarDistinto ? v.domicilioFamiliarLocalidad : null,
        domicilioFamiliarProvincia: v.domicilioFamiliarDistinto ? v.domicilioFamiliarProvincia : null,
      },
      categoriaBinid: v.categoriaBinid,
      carreraGrado: v.carreraGrado,
      anioIngreso: v.anioIngreso,
      anioEgreso: this.esGraduadoOEgresado ? v.anioEgreso : null,
      materiasCursadas: this.esEstudianteAvanzado ? v.materiasCursadas : null,
      promedioConAplazos: v.promedioConAplazos,
      promedioSinAplazos: v.promedioSinAplazos || null,
      accedioABecaBinidAnterior: v.accedioABecaBinidAnterior,
      nombreDirectorProyecto: v.nombreDirectorProyecto,
      apellidoDirectorProyecto: v.apellidoDirectorProyecto,
      pregunta: v.pregunta,
    };

    try {
      const postulacion = await firstValueFrom(this.postulacionService.postularBinid(body));
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

    Object.entries(this.archivos).forEach(([clave, archivosArray]) => {
      const tipo = this.mapaClaveATipo[clave];
      archivosArray.forEach((archivo) => {
        const formData = new FormData();
        formData.append('file', archivo);
        formData.append('tipoArchivo', tipo);
        uploads.push(firstValueFrom(this.archivoService.subir(postulacionId, formData)));
      });
    });

    await Promise.all(uploads);
  }
}
