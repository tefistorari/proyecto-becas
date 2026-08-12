import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { ConvocatoriaResponse } from '../../../models/convocatoria-response';
import { EstadoConvocatoria } from '../../../models/estado-convocatoria';

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

  EstadoConvocatoria = EstadoConvocatoria;

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.convocatoriaService.buscarPorId(id).subscribe({
      next: (data) => {
        this.convocatoria.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se puedo cargar la convocatoria');
        this.cargando.set(false);
      }
    });
  }

  cancelar(): void {
    const actual = this.convocatoria();
    if(!actual) return;

    const confirmar = confirm('¿Seguro que querés cancelar esta convocatoria?');
    if(!confirmar) return;

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
}
