import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostulacionService } from '../../../services/postulacion-service';
import { PostulacionResponse } from '../../../models/postulacion-response';
import { EstadoPostulacion } from '../../../models/estado-postulacion';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mis-postulaciones',
  imports: [CommonModule],
  templateUrl: './mis-postulaciones.html',
  styleUrl: './mis-postulaciones.css',
})
export class MisPostulaciones implements OnInit {
  private postulacionService = inject(PostulacionService);
  private router = inject(Router);

  postulaciones = signal<PostulacionResponse[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  postulacionSeleccionada = signal<PostulacionResponse | null>(null);

  protected readonly EstadoPostulacion = EstadoPostulacion;

  ngOnInit(): void {
    this.cargarMisPostulaciones();
  }

  private cargarMisPostulaciones(): void {
    this.postulacionService.listarMisPostulaciones().subscribe({
      next: (data) => {
        this.postulaciones.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar tus postulaciones.');
        this.cargando.set(false);
      }
    });
  }

  verDetalle(postulacion: PostulacionResponse): void {
    this.postulacionSeleccionada.set(postulacion);
  }

  cerrarDetalle(): void {
    this.postulacionSeleccionada.set(null);
  }

  tipoBeca(postulacion: PostulacionResponse): string {
    if (postulacion.becaBaseBis) return 'BASE/BIS';
    if (postulacion.becaBinid) return 'BINID';
    if (postulacion.becaCarreraInvestigador) return 'Carrera Investigador';
    return '—';
  }

  badgeEstado(estado: EstadoPostulacion): string {
    switch (estado) {
      case EstadoPostulacion.BORRADOR:     return 'borrador';
      case EstadoPostulacion.PENDIENTE:    return 'pendiente';
      case EstadoPostulacion.EN_REVISION:  return 'revision';
      case EstadoPostulacion.ACEPTADO:     return 'aceptado';
      case EstadoPostulacion.RECHAZADO:    return 'rechazado';
      default: return '';
    }
  }

  volver(): void {
    this.router.navigate(['/alumno/dashboard']);
  }
}
