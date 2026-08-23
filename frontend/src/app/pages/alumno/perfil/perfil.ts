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
export class Perfil implements OnInit{
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


  form = this.fb.group({
    dni: ['', Validators.required],
    fechaNacimiento: ['', Validators.required],
    genero: [null as Genero | null, Validators.required],
    celular: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]], //solo numeros- pattern cambia si permito espacio y guiones

    domicilioCalle: ['', Validators.required],
    domicilioNumero: [null as number | null, Validators.required],
    domicilioPisoDepto: [''],

    codigoPostal: ['', Validators.required],
    localidad: ['', Validators.required],
    provincia: ['', Validators.required],
    nacionalidad: ['', Validators.required],

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

    this.perfilService.obtenerMisDatos().subscribe({
      next: (data) => {
        this.modo.set('editar');
        this.form.patchValue(data);
        
        //cargar localidades de la provincia guardada
        if(data.provincia) {
          this.cargarLocalidades(data.provincia);
        }
        if(data.domicilioFamiliarDistinto && data.domicilioFamiliarProvincia) {
          this.cargarLocalidadesFamiliar(data.domicilioFamiliarProvincia);
        }

        this.cargando.set(false);
      },
      error: () => {
        this.modo.set('crear');
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

  guardar(): void {
    if(this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

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
        console.error(err);
      }
    })
  }
}
