import { Component, inject, signal } from '@angular/core';
import { BecaResponse } from '../../../models/beca-response';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { BecaService } from '../../../services/beca-service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { fechaNoPasadaValidator } from '../../../validators/convocatoria-validators';

@Component({
  selector: 'app-convocatoria-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './convocatoria-form.html',
  styleUrl: './convocatoria-form.css',
})
export class ConvocatoriaForm {
  private fb = inject(FormBuilder);
  private convocatoriaService = inject(ConvocatoriaService);
  private becaService = inject(BecaService);
  private router = inject(Router);

  private readonly anioActual = new Date().getFullYear();

  becas = signal<BecaResponse[]>([]);
  error = signal<string | null>(null);
  guardando = signal(false);

  form = this.fb.group({
    becaId: [null as number | null, Validators.required],
    anio: [this.anioActual, [Validators.required, Validators.min(this.anioActual)]],
    fechaApertura: ['', [Validators.required, fechaNoPasadaValidator()]],
    fechaCierre: ['', [Validators.required, fechaNoPasadaValidator()]],
    descripcion: [''],
    cupoMaximo: [null as number | null, [Validators.required, Validators.min(1)]]
  });

  constructor() {
    this.becaService.listarTodas().subscribe({
      next: (becas) => this.becas.set(becas),
      error: () => this.error.set('No se pudieron cargar las becas')
    })
  }

  onSubmit(): void {
    if(this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();
    
    //Validacion extra que backend no chequea sola: cierre > apertura
    if(new Date(raw.fechaCierre!) <= new Date(raw.fechaApertura!)) {
      this.error.set('La fecha de cierre debe ser posterior a la apertura');
      return;
    }

    this.guardando.set(true);
    this.error.set(null);

    this.convocatoriaService.crear({
      becaId: raw.becaId!,
      anio: raw.anio!,
      fechaApertura: `${raw.fechaApertura}:00`,
      fechaCierre: `${raw.fechaCierre}:00`,
      descripcion: raw.descripcion ?? '',
      cupoMaximo: raw.cupoMaximo!
    }).subscribe({
      next: () => this.router.navigate(['admin/convocatorias']),
      error: (err) => {
        this.guardando.set(false);
        this.error.set(err.error?.message ?? 'Error al crear la convocatoria');
      }
    });
  }

  // helper para el HTML: ¿mostrar error en este campo?
    invalido(campo: string): boolean {
        const control = this.form.get(campo);
        return !!control && control.invalid && (control.touched || control.dirty);
    }

    mensajeError(campo: string): string {
      const control = this.form.get(campo);
      if(!control?.errors) return '';

      if(control.errors['required']) return 'Este campo es obligatorio';
      if(control.errors['min'] && campo === 'anio') return `El año no puede ser menor a ${this.anioActual}`;
      if(control.errors['min']) return 'Debe ser mayor a cero';
      if(control.errors['fechaPasada']) return 'La fecha no puede ser anterior a hoy';

      return 'Valor invalido';
    }
}
