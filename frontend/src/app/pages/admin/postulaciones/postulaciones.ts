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
  private route = inject(ActivatedRoute);
  private convocatoriaService = inject(ConvocatoriaService);
  private ubicacionService = inject(UbicacionService);

  archivos = signal<ArchivoResponse[]>([]);

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

  /** Chips de estado (el orden es el que se muestra) */
  readonly filtros: { valor: Filtro; texto: string }[] = [
    { valor: 'TODAS', texto: 'Todas' },
    { valor: EstadoPostulacion.BORRADOR, texto: 'Borrador' },
    { valor: EstadoPostulacion.PENDIENTE, texto: 'Pendientes' },
    { valor: EstadoPostulacion.EN_REVISION, texto: 'En revisión' },
    { valor: EstadoPostulacion.ACEPTADO, texto: 'Aceptadas' },
    { valor: EstadoPostulacion.RECHAZADO, texto: 'Rechazadas' },
  ];

  // ---------- Derivados ----------

  /** Convocatoria elegida (null = todas) */
  convocatoriaActual = computed(() => {
    const id = this.convocatoriaId();
    return id === null ? null : this.convocatorias().find(c => c.id === id) ?? null;
  });

  /** Postulaciones de la convocatoria elegida, sin filtrar por estado */
  postulacionesDeConvocatoria = computed(() => {
    const id = this.convocatoriaId();
    const todas = this.todas();
    return id === null ? todas : todas.filter(p => p.convocatoria.id === id);
  });

  /** Lo que se ve en la tabla: convocatoria + estado */
  postulacionesFiltradas = computed(() => {
    const filtro = this.filtroEstado();
    const lista = this.postulacionesDeConvocatoria();
    return filtro === 'TODAS' ? lista : lista.filter(p => p.estado === filtro);
  });

  /** Cantidad por estado dentro de la convocatoria elegida (para los chips) */
  conteoPorEstado = computed(() => {
    const lista = this.postulacionesDeConvocatoria();
    const conteo: Record<string, number> = { TODAS: lista.length };
    for (const p of lista) conteo[p.estado] = (conteo[p.estado] ?? 0) + 1;
    return conteo;
  });

  /** Total y pendientes por convocatoria (para el panel izquierdo) */
  conteoPorConvocatoria = computed(() => {
    const mapa = new Map<number, { total: number; pendientes: number }>();
    for (const p of this.todas()) {
      const actual = mapa.get(p.convocatoria.id) ?? { total: 0, pendientes: 0 };
      actual.total++;
      if (p.estado === EstadoPostulacion.PENDIENTE) actual.pendientes++;
      mapa.set(p.convocatoria.id, actual);
    }
    return mapa;
  });

  /** Convocatorias agrupadas por año, del más nuevo al más viejo */
  convocatoriasPorAnio = computed(() => {
    const grupos = new Map<number, ConvocatoriaResponse[]>();
    for (const c of this.convocatorias()) {
      grupos.set(c.anio, [...(grupos.get(c.anio) ?? []), c]);
    }
    return [...grupos.entries()]
      .sort((a, b) => b[0] - a[0])
      .map(([anio, items]) => ({ anio, items }));
  });

  // ---------- Carga ----------

  ngOnInit(): void {
    // Se mantiene el link ?convocatoriaId=… (por ejemplo desde la pantalla de convocatorias)
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

    this.postulacionService.listarTodas().subscribe({
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

  // ---------- Selección ----------

  seleccionarConvocatoria(id: number | null): void {
    this.convocatoriaId.set(id);
    this.filtroEstado.set('TODAS');
  }

  /** Para el <select> de mobile */
  setConvocatoria(valor: string): void {
    this.seleccionarConvocatoria(valor ? Number(valor) : null);
  }

  setFiltro(filtro: Filtro): void {
    this.filtroEstado.set(filtro);
  }

  etiquetaEstado(estado: string): string {
    return estado.replace('_', ' ');
  }

  // ---------- Detalle ----------

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
        // Al actualizar `todas`, los contadores del panel y de los chips se recalculan solos
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