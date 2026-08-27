import { CommonModule } from '@angular/common';
import { Component, inject, input, OnInit, signal } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LabelEnumPipe } from '../../../../pipes/label-enum-pipe';
import { Router } from '@angular/router';
import { PerfilService } from '../../../../services/perfil-service';
import { PostulacionService } from '../../../../services/postulacion-service';
import { ArchivoService } from '../../../../services/archivo-service';
import { TipoDocumento } from '../../../../models/tipo-documento';
import { Genero } from '../../../../models/genero';
import { CondicionLaboral } from '../../../../models/condicion-laboral';
import { Salud } from '../../../../models/salud';
import { NivelMateria } from '../../../../models/nivel-materia';
import { MesMesa } from '../../../../models/mes-mesa';
import { Parentesco } from '../../../../models/parentesco';
import { RegimenMateria } from '../../../../models/regimen-materia';
import { PostulacionBaseBisRequest } from '../../../../models/postulacion-base-bis-request';
import { firstValueFrom } from 'rxjs';


const MAX_MB_POR_ARCHIVO = 5;
@Component({
  selector: 'app-formulario-bis',
  imports: [ReactiveFormsModule, CommonModule, LabelEnumPipe],
  templateUrl: './formulario-bis.html',
  styleUrl: './formulario-bis.css',
})
export class FormularioBis implements OnInit{
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private perfilService = inject(PerfilService);
  private postulacionService = inject(PostulacionService);
  private archivoService = inject(ArchivoService);
 
  // Input que recibe el id de la convocatoria seleccionada
  readonly convocatoriaId = input.required<number>();
 
  form!: FormGroup;
  cargandoDatos = signal(true);
  enviando = false;
  intentoEnvio = false;
  erroresArchivo: string[] = [];
 
  // Para referenciar tipoDocumento.DNI, etc. desde el template
  tipoDocumento = TipoDocumento;
  // Archivos seleccionados por tipo
  archivos: Partial<Record<TipoDocumento, File[]>> = {};
 
  // Enums
  generos = Object.values(Genero);
  condicionesLaborales = Object.values(CondicionLaboral);
  tiposSalud = Object.values(Salud);
  nivelesMaterias = Object.values(NivelMateria);
  mesesMesa = Object.values(MesMesa);
  parentescos = Object.values(Parentesco);
  regimenesMateria = Object.values(RegimenMateria);
 
  anioActual = new Date().getFullYear();
 
  ngOnInit(): void {
    this.inicializarForm();
    this.cargarDatosPersonales();
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
 
      // Datos BASE/BIS
      carrera: ['', Validators.required],
      tipoVivienda: ['', Validators.required],
      tipoViviendaDetalle: [''],
      condicionLaboral: [null as CondicionLaboral | null, Validators.required],
      salud: [null as Salud | null, Validators.required],
      tieneCondicionSalud: [false],
      detalleCondicionSalud: [''],
 
      // FormArrays
      materiasACursar: this.fb.array([this.crearMateriaACursar()]),
      materiasARendir: this.fb.array([this.crearMateriaARendir()]),
      grupoFamiliar: this.fb.array([this.crearIntegrante()]),
 
      // Declaración
      aceptaDeclaracion: [false, Validators.requiredTrue],
    });
  }
 
  // --- Carga de datos personales del perfil ---
  // Reutiliza PerfilService, que ya existe y ya funciona (mismo que usa Perfil).
 
  cargarDatosPersonales(): void {
    this.perfilService.obtenerMisDatos().subscribe({
      next: (datos: any) => {
        this.form.patchValue({
          dpNombre: datos.nombre,
          dpApellido: datos.apellido,
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
        });
        this.cargandoDatos.set(false);
      },
      error: () => {
        this.cargandoDatos.set(false);
      },
    });
  }
 
  // --- FormArrays: Materias a cursar ---
 
  get materiasACursar(): FormArray {
    return this.form.get('materiasACursar') as FormArray;
  }
 
  crearMateriaACursar(): FormGroup {
    return this.fb.group({
      nombreMateria: ['', Validators.required],
      nivelMateria: [null as NivelMateria | null, Validators.required],
      regimenMateria: ['CUATRIMESTRAL' as RegimenMateria, Validators.required],
      anioMateria: [this.anioActual, Validators.required],
    });
  }
 
  agregarMateriaCursar(): void {
    this.materiasACursar.push(this.crearMateriaACursar());
  }
 
  removerMateriaCursar(index: number): void {
    this.materiasACursar.removeAt(index);
  }
 
  // --- FormArrays: Materias a rendir ---
 
  get materiasARendir(): FormArray {
    return this.form.get('materiasARendir') as FormArray;
  }
 
  crearMateriaARendir(): FormGroup {
    return this.fb.group({
      nombreMateria: ['', Validators.required],
      nivelMateria: [null as NivelMateria | null, Validators.required],
      mesMesa: [null as MesMesa | null, Validators.required],
      anioMesa: [this.anioActual, Validators.required],
    });
  }
 
  agregarMateriaRendir(): void {
    this.materiasARendir.push(this.crearMateriaARendir());
  }
 
  removerMateriaRendir(index: number): void {
    this.materiasARendir.removeAt(index);
  }
 
  // --- FormArrays: Grupo familiar ---
 
  get integrantes(): FormArray {
    return this.form.get('grupoFamiliar') as FormArray;
  }
 
  crearIntegrante(): FormGroup {
    return this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', Validators.required],
      parentesco: [null as Parentesco | null, Validators.required],
      ocupacion: ['', Validators.required],
      ingreso: ['', Validators.required],
    });
  }
 
  agregarIntegrante(): void {
    this.integrantes.push(this.crearIntegrante());
  }
 
  removerIntegrante(index: number): void {
    this.integrantes.removeAt(index);
  }
 
  // --- Archivos ---
 
  onArchivoSeleccionado(event: Event, tipo: TipoDocumento): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const archivosArray = Array.from(input.files);
      // Validar tamaño máximo 5MB por archivo
      const invalidos = archivosArray.filter((f) => f.size > MAX_MB_POR_ARCHIVO * 1024 * 1024);
      if (invalidos.length > 0) {
        this.erroresArchivo.push(`El archivo "${invalidos[0].name}" supera el tamaño máximo de ${MAX_MB_POR_ARCHIVO} MB.`);
        return;
      }
      this.archivos[tipo] = archivosArray;
      this.erroresArchivo = [];
    }
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
 
    if (!this.archivos[TipoDocumento.DNI] || this.archivos[TipoDocumento.DNI]!.length === 0) {
      this.erroresArchivo.push('El DNI es obligatorio.');
      return;
    }
 
    this.enviando = true;
    const v = this.form.getRawValue();
 
    // Tipado con PostulacionBaseBisRequest en vez de objeto suelto:
    // así TS avisa si falta o sobra algún campo respecto al @RequestBody del backend.
    const body: PostulacionBaseBisRequest = {
      convocatoriaId: this.convocatoriaId(), // <- es un signal, hay que llamarlo
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
        domicilioFamiliarDistinto: false,
      },
      tipoVivienda: v.tipoVivienda === 'otra' ? v.tipoViviendaDetalle : v.tipoVivienda,
      condicionLaboral: v.condicionLaboral,
      carrera: v.carrera,
      salud: v.salud,
      tieneCondicionSalud: v.tieneCondicionSalud,
      detalleCondicionSalud: v.tieneCondicionSalud ? v.detalleCondicionSalud : null,
      grupoFamiliar: v.grupoFamiliar,
      materiasACursar: v.materiasACursar,
      materiasARendir: v.materiasARendir,
    };
 
    try {
      // Paso 1 — Postularse
      const postulacion = await firstValueFrom(this.postulacionService.postularBaseBis(body));
      // Paso 2 — Subir archivos
      await this.subirArchivos(postulacion.id);
      // Paso 3 — Finalizar (POST, según PostulacionController)
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
