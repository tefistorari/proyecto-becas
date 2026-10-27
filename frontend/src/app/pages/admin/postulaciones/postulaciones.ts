import { Component, inject, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostulacionService } from '../../../services/postulacion-service';
import { PostulacionResponse } from '../../../models/postulacion-response';
import { EstadoPostulacion } from '../../../models/estado-postulacion';
import { ArchivoService } from '../../../services/archivo-service';
import { ArchivoResponse } from '../../../models/archivo-response';
import { ActivatedRoute } from '@angular/router';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { ConvocatoriaResponse } from '../../../models/convocatoria-response';

type Filtro = EstadoPostulacion | 'TODAS';

@Component({
  selector: 'app-postulaciones',
  imports: [CommonModule],
  templateUrl: './postulaciones.html',
  styleUrl: './postulaciones.css',
})
export class Postulaciones implements OnInit {
  private postulacionService = inject(PostulacionService);
  private archivoService = inject(ArchivoService);
  archivos = signal<ArchivoResponse[]>([]);
  private route = inject(ActivatedRoute);
  private convocatoriaService = inject(ConvocatoriaService);

  convocatorias = signal<ConvocatoriaResponse[]>([]);
  convocatoriaId = signal<number | null>(null);

  todas = signal<PostulacionResponse[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  filtroEstado = signal<Filtro>('TODAS');
  postulacionSeleccionada = signal<PostulacionResponse | null>(null);
  cambiandoEstado = signal(false);
  errorEstado = signal<string | null>(null);

  protected readonly EstadoPostulacion = EstadoPostulacion;

  postulacionesFiltradas = computed(() => {
    const filtro = this.filtroEstado();
    if (filtro === 'TODAS') return this.todas();
    return this.todas().filter(p => p.estado === filtro);
  });

  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('convocatoriaId');
    this.convocatoriaId.set(id ? Number(id) : null);

    this.convocatoriaService.listarTodas().subscribe({
      next: (lista) => this.convocatorias.set(lista),
      error: (err) => console.error('Error al cargar convocatorias:', err),
    });

    this.cargar();
  }

  private cargar(): void {
    this.cargando.set(true);
    this.error.set(null);

    const id = this.convocatoriaId();
    const peticion = id
      ? this.postulacionService.listarPorConvocatoria(id)
      : this.postulacionService.listarTodas();

    peticion.subscribe({
      next: (data) => {
        this.todas.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las postulaciones.');
        this.cargando.set(false);
      },
    });
  }

  setConvocatoria(valor: string): void {
    this.convocatoriaId.set(valor ? Number(valor) : null);
    this.cargar();
  }

  private cargarTodas(): void {
    this.cargando.set(true);
    this.postulacionService.listarTodas().subscribe({
      next: (data) => {
        this.todas.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las postulaciones.');
        this.cargando.set(false);
      }
    });
  }

  setFiltro(filtro: Filtro): void {
    this.filtroEstado.set(filtro);
  }

  verDetalle(postulacion: PostulacionResponse): void {
    this.postulacionSeleccionada.set(postulacion);
    this.errorEstado.set(null);
    this.archivos.set([]);
    this.archivoService.listarPorPostulacion(postulacion.id).subscribe({
      next: (lista) => this.archivos.set(lista),
      error: (err) => console.error('Error al cargar archivos:', err),
    });
  }

  verArchivo(id: number): void {
    // Se abre la pestaña ya, en el clic, para que el navegador no la bloquee
    const ventana = window.open('', '_blank');
    this.archivoService.descargar(id).subscribe({
      next: (blob) => {
        const url = URL.createObjectURL(blob);
        if (ventana) ventana.location.href = url;
        setTimeout(() => URL.revokeObjectURL(url), 60_000);
      },
      error: (err) => {
        ventana?.close();
        console.error('Error al abrir el archivo:', err);
      },
    });
  }

  cerrarDetalle(): void {
    this.postulacionSeleccionada.set(null);
    this.errorEstado.set(null);
  }

  cambiarEstado(postulacion: PostulacionResponse, estado: EstadoPostulacion): void {
    const confirmar = confirm(`¿Seguro que querés cambiar el estado a ${estado}?`);
    if (!confirmar) return;

    this.cambiandoEstado.set(true);
    this.errorEstado.set(null);

    this.postulacionService.cambiarEstado(postulacion.id, estado).subscribe({
      next: (actualizada) => {
        this.todas.update(lista =>
          lista.map(p => p.id === actualizada.id ? actualizada : p)
        );
        this.postulacionSeleccionada.set(actualizada);
        this.cambiandoEstado.set(false);
      },
      error: () => {
        this.errorEstado.set('No se pudo cambiar el estado.');
        this.cambiandoEstado.set(false);
      }
    });
  }

  tipoBeca(postulacion: PostulacionResponse): string {
    if (postulacion.becaBaseBis) return 'BASE/BIS';
    if (postulacion.becaBinid) return 'BINID';
    if (postulacion.becaCarreraInvestigador) return 'Carrera Investigador';
    return '—';
  }
}
