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
import { UbicacionService } from '../../../services/ubicacion-service';

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
  private ubicacionService = inject(UbicacionService);

  convocatorias = signal<ConvocatoriaResponse[]>([]);
  convocatoriaId = signal<number | null>(null);

  todas = signal<PostulacionResponse[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  filtroEstado = signal<Filtro>('TODAS');
  postulacionSeleccionada = signal<PostulacionResponse | null>(null);
  cambiandoEstado = signal(false);
  errorEstado = signal<string | null>(null);

  nacionalidadNombre = signal('');
  provinciaNombre = signal('');
  localidadNombre = signal('');
  provinciaFamiliarNombre = signal('');
  localidadFamiliarNombre = signal('');
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

  async verDetalle(postulacion: PostulacionResponse): Promise<void> {
    this.limpiarUbicaciones();
    this.postulacionSeleccionada.set(postulacion);
    this.errorEstado.set(null);

    this.archivos.set([]);
    this.archivoService.listarPorPostulacion(postulacion.id).subscribe({
      next: (lista) => this.archivos.set(lista),
      error: (err) => console.error('Error al cargar archivos:', err),
    });

    const d = postulacion.datosPersonalesHistorial;
    if (!d) return;

    const nacionalidades = await this.ubicacionService.getNacionalidades();
    const provincias = await this.ubicacionService.getProvincias();

    const nacionalidad = nacionalidades.find(n => n.iso2 === d.nacionalidad);
    const provincia = provincias.find(p => p.iso2 === d.provincia);

    this.nacionalidadNombre.set(nacionalidad?.name ?? d.nacionalidad);
    this.provinciaNombre.set(provincia?.name ?? d.provincia);

    if (d.provincia && d.localidad) {
      const localidades = await this.ubicacionService.getLocalidades(d.provincia);
      const localidad = localidades.find(l => String(l.id) === String(d.localidad));
      this.localidadNombre.set(localidad?.name ?? String(d.localidad));
    }

    if (d.domicilioFamiliarDistinto && d.domicilioFamiliarProvincia) {
      const provFam = provincias.find(p => p.iso2 === d.domicilioFamiliarProvincia);
      this.provinciaFamiliarNombre.set(provFam?.name ?? d.domicilioFamiliarProvincia);

      if (d.domicilioFamiliarLocalidad) {
        const locsFam = await this.ubicacionService.getLocalidades(d.domicilioFamiliarProvincia);
        const locFam = locsFam.find(l => String(l.id) === String(d.domicilioFamiliarLocalidad));
        this.localidadFamiliarNombre.set(locFam?.name ?? String(d.domicilioFamiliarLocalidad));
      }
    }
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
    this.archivos.set([]);
    this.limpiarUbicaciones();
  }

  private limpiarUbicaciones(): void {
    this.nacionalidadNombre.set('');
    this.provinciaNombre.set('');
    this.localidadNombre.set('');
    this.provinciaFamiliarNombre.set('');
    this.localidadFamiliarNombre.set('');
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
