import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { ConvocatoriaResponse } from '../../../models/convocatoria-response';
import { EstadoConvocatoria } from '../../../models/estado-convocatoria';
import { EstadisticasConvocatoriaResponse } from '../../../models/estadisticas-convocatoria-response';
import { EstadoPostulacion } from '../../../models/estado-postulacion';

@Component({
  selector: 'app-convocatoria-detalle',
  imports: [CommonModule],
  templateUrl: './convocatoria-detalle.html',
  styleUrl: './convocatoria-detalle.css',
})
export class ConvocatoriaDetalle implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private convocatoriaService = inject(ConvocatoriaService);

  convocatoria = signal<ConvocatoriaResponse | null>(null);
  cargando = signal(true);
  error = signal<string | null>(null);
  cancelando = signal(false);
  estadisticas = signal<EstadisticasConvocatoriaResponse | null>(null);


  EstadoConvocatoria = EstadoConvocatoria;

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.convocatoriaService.buscarPorId(id).subscribe({
      next: (data) => {
        this.convocatoria.set(data);
        this.cargando.set(false);
        this.convocatoriaService.obtenerEstadisticas(id).subscribe({
          next: (stats) => this.estadisticas.set(stats),
          error: () => { } // si fallan, el detalle se muestra igual sin estadísticas
        });
      },
      error: () => {
        this.error.set('No se puedo cargar la convocatoria');
        this.cargando.set(false);
      }
    });
  }

  // helpers para el html
  cantidad(estado: EstadoPostulacion): number {
    return this.estadisticas()?.porEstado[estado] ?? 0;
  }

  get tasaAceptacion(): number {
    const aceptadas = this.cantidad(EstadoPostulacion.ACEPTADO);
    const resueltas = aceptadas + this.cantidad(EstadoPostulacion.RECHAZADO);
    return resueltas > 0 ? Math.round((aceptadas * 100) / resueltas) : 0;
  }

  get avanceRevision(): number {
    const stats = this.estadisticas();
    if (!stats) return 0;
    const finalizadas = stats.totalPostulaciones - this.cantidad(EstadoPostulacion.BORRADOR);
    const resueltas = this.cantidad(EstadoPostulacion.ACEPTADO) + this.cantidad(EstadoPostulacion.RECHAZADO);
    return finalizadas > 0 ? Math.round((resueltas * 100) / finalizadas) : 0;
  }

  EstadoPostulacion = EstadoPostulacion;

  cancelar(): void {
    const actual = this.convocatoria();
    if (!actual) return;

    const confirmar = confirm('¿Seguro que querés cancelar esta convocatoria?');
    if (!confirmar) return;

    this.cancelando.set(true);

    this.convocatoriaService.cambiarEstado(actual.id, EstadoConvocatoria.CANCELADA).subscribe({
      next: (actualizada) => {
        this.convocatoria.set(actualizada);
        this.cancelando.set(false);
      },
      error: () => {
        this.error.set('No se pudo cancelar la convocatoria');
        this.cancelando.set(false);
      }
    });
  }

  volver(): void {
    this.router.navigate(['/admin/convocatorias']);
  }

  verPostulaciones(id: number): void {
    this.router.navigate(['/admin/postulaciones'], { queryParams: { convocatoriaId: id } });
  }
}
