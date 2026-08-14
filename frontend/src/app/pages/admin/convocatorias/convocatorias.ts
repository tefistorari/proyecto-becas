import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { BecaService } from '../../../services/beca-service';
import { Router } from '@angular/router';
import { BecaResponse } from '../../../models/beca-response';
import { CommonModule } from '@angular/common';
import { ConvocatoriaResponse } from '../../../models/convocatoria-response';
import { EstadoConvocatoria } from '../../../models/estado-convocatoria';

type Filtro = EstadoConvocatoria | 'TODAS';

@Component({
  selector: 'app-convocatorias',
  imports: [CommonModule],
  templateUrl: './convocatorias.html',
  styleUrl: './convocatorias.css',
})
export class Convocatorias implements OnInit {
  private convocatoriaService = inject(ConvocatoriaService);
  private router = inject(Router);

  todas = signal<ConvocatoriaResponse[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  filtroEstado = signal<Filtro>('TODAS');

  abiertas = signal<ConvocatoriaResponse[] | null>(null);

  convocatoriasFiltradas = computed(() => {
    const filtro = this.filtroEstado();

    if(filtro === EstadoConvocatoria.ABIERTA) {
      return this.abiertas() ?? [];
    }
    if(filtro === 'TODAS') {
      return this.todas();
    }
    return this.todas().filter(c => c.estado === filtro);
  });

  protected readonly EstadoConvocatoria = EstadoConvocatoria;

  ngOnInit(): void {
    this.cargarTodas();
  }

  private cargarTodas() {
    this.cargando.set(true);
    this.convocatoriaService.listarTodas().subscribe({
      next: (data) => {
        this.todas.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las convocatorias.');
        this.cargando.set(false);
      }
    });
  }

  setFiltro(estado: Filtro): void {
    this.filtroEstado.set(estado);

    if(estado === EstadoConvocatoria.ABIERTA && this.abiertas() === null) {
      this.cargando.set(true);
      this.convocatoriaService.listarAbiertas().subscribe({
        next: (data) => {
          this.abiertas.set(data);
          this.cargando.set(false);
        },
        error: () => {
          this.error.set('No se pudieron cargar las convocatorias abiertas.');
          this.cargando.set(false);
        }
      });
    }
  }

  nuevaConvocatoria(): void {
    this.router.navigate(['admin/convocatorias/nueva']);
  }
  
  verDetalle(id: number): void {
    this.router.navigate(['/admin/convocatorias', id]);
  }
}
