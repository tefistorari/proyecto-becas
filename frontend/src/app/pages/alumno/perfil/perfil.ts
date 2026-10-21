import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { PerfilService } from '../../../services/perfil-service';
import { Genero } from '../../../models/genero';
import { DatosPersonalesRequest } from '../../../models/datos-personales-request';
import { UbicacionService } from '../../../services/ubicacion-service';

@Component({
  selector: 'app-perfil',
  imports: [ReactiveFormsModule],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css',
})
export class Perfil implements OnInit {
  private fb = inject(FormBuilder);
  private perfilService = inject(PerfilService);
  private ubicacionService = inject(UbicacionService);

  nacionalidades = signal<any[]>([]);
  provincias = signal<any[]>([]);
  localidades = signal<any[]>([]);

  provinciasFamiliar = signal<any[]>([]);
  localidadesFamiliar = signal<any[]>([]);

  modo = signal<'crear' | 'editar'>('crear');
  cargando = signal(true);
  guardando = signal(false);
  error = signal<string | null>(null);

  private readonly camposFamiliares = [
    'domicilioFamiliarCalle',
    'domicilioFamiliarNumero',
    'domicilioFamiliarCodigoPostal',
    'domicilioFamiliarProvincia',
    'domicilioFamiliarLocalidad',
  ] as const;


  form = this.fb.group({
    dni: ['', [Validators.required, Validators.pattern(/^\d{7,8}$/)]],
    fechaNacimiento: ['', Validators.required],
    genero: [null as Genero | null, Validators.required],
    celular: ['', [Validators.required, Validators.pattern(/^[1-9][0-9]{9,10}$/)]], //solo numeros- pattern cambia si permito espacio y guiones

    domicilioCalle: ['', Validators.required],
    domicilioNumero: [null as number | null, Validators.required],
    domicilioPisoDepto: [''],

    codigoPostal: ['', Validators.required],
    localidad: ['', Validators.required],
    provincia: ['', Validators.required],
    nacionalidad: ['', Validators.required],
    cbuCvu: ['', [Validators.required, Validators.pattern(/^\d{22}$/)]],

    domicilioFamiliarDistinto: [false, Validators.required],

    domicilioFamiliarCalle: [''],
    domicilioFamiliarNumero: [null as number | null],
    domicilioFamiliarPisoDepto: [''],
    domicilioFamiliarCodigoPostal: [''],
    domicilioFamiliarLocalidad: [''],
    domicilioFamiliarProvincia: [''],
  });


  ngOnInit(): void {
    this.cargarNacionalidades();
    this.cargarProvincias();


    this.ubicacionService.getProvincias()
      .then(p => this.provinciasFamiliar.set(p));

    this.form.get('domicilioFamiliarDistinto')!.valueChanges.subscribe(distinto => {
      this.actualizarValidadoresFamiliares(!!distinto);
    });

    this.perfilService.obtenerMisDatos().subscribe({
      next: (data) => {
        this.modo.set('editar');
        this.form.patchValue(data);

        //cargar localidades de la provincia guardada
        if (data.provincia) {
          this.cargarLocalidades(data.provincia);
        }
        if (data.domicilioFamiliarDistinto && data.domicilioFamiliarProvincia) {
          this.cargarLocalidadesFamiliar(data.domicilioFamiliarProvincia);
        }

        this.cargando.set(false);
      },
      error: (err) => {
        if (err.status === 404) {
          this.modo.set('crear');
        } else {
          console.error('Error al cargar datos personales', err);
        }
        this.cargando.set(false);
      }
    });

  }

  cargarNacionalidades(): void {
    this.ubicacionService.getNacionalidades()
      .then(n => this.nacionalidades.set(n));
  }

  cargarProvincias(): void {
    this.localidades.set([]);
    this.ubicacionService.getProvincias()
      .then(p => this.provincias.set(p));
  }

  cargarLocalidades(codigoProvincia: string): void {
    this.localidades.set([]);
    this.ubicacionService.getLocalidades(codigoProvincia)
      .then(l => this.localidades.set(l));
  }

  cargarLocalidadesFamiliar(codigoProvincia: string): void {
    this.localidadesFamiliar.set([]);
    this.ubicacionService.getLocalidades(codigoProvincia)
      .then(l => this.localidadesFamiliar.set(l));
  }

  private actualizarValidadoresFamiliares(distinto: boolean): void {
    for (const campo of this.camposFamiliares) {
      const control = this.form.get(campo)!;
      if (distinto) {
        control.setValidators(Validators.required);
      } else {
        control.clearValidators();
        control.reset(campo === 'domicilioFamiliarNumero' ? null : '');
      }
      control.updateValueAndValidity({ emitEvent: false });
    }
    if (!distinto) {
      this.form.get('domicilioFamiliarPisoDepto')!.reset('');
      this.localidadesFamiliar.set([]);
    }
  }
  
    guardar(): void {
      if(this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.error.set(null);
    this.guardando.set(true);
    const request = this.form.value as DatosPersonalesRequest;

    const peticion = this.modo() === 'crear'
      ? this.perfilService.crear(request)
      : this.perfilService.actualizar(request);

    peticion.subscribe({
      next: () => {
        this.guardando.set(false);
        this.modo.set('editar');
      },
      error: (err) => {
        this.guardando.set(false);
        this.error.set(err.error?.error ?? 'No se pudieron guardar los datos');
      }
    });
  }

}
