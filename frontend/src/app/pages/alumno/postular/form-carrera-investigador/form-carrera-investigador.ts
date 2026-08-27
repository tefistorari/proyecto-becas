import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostulacionService } from '../../../../services/postulacion-service';
import { PerfilService } from '../../../../services/perfil-service';
import { UbicacionService } from '../../../../services/ubicacion-service';
import { ActivatedRoute, Router } from '@angular/router';

const MAX_MB_POR_ARCHIVO = 5;
@Component({
  selector: 'app-form-carrera-investigador',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './form-carrera-investigador.html',
  styleUrl: './form-carrera-investigador.css',
})
export class FormCarreraInvestigador {
  private fb = inject(FormBuilder);
  private postulacionService = inject(PostulacionService);
  private perfilService = inject(PerfilService);
  private ubicacionService = inject(UbicacionService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  cargando = signal(false);
  error = signal('');
  mensaje = signal('');

  provincias = signal<any[]>([]);
  localidades = signal<any[]>([]);
  localidadesFamiliar = signal<any[]>([]);

  convocatoriaId!: number;
  postulacionId: number | null = null;

  archivoFormulario: File | null = null;
  archivoCV: File | null = null;

  formulario = this.fb.group({
    datosPersonales: this.fb.group({
      dni: ['', Validators.required],
      fechaNacimiento: ['', Validators.required],
      genero: ['', Validators.required],
      celular: ['', [Validators.required, Validators.pattern(/^[1-9][0-9]{9,10}$/)]],

      domicilioCalle: ['', Validators.required],
      domicilioNumero: ['', Validators.required],
    })
  })
}
