import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostulacionService } from '../../../services/postulacion-service';
import { PostulacionResponse } from '../../../models/postulacion-response';
import { EstadoPostulacion } from '../../../models/estado-postulacion';
import { Router } from '@angular/router';
import { UbicacionService } from '../../../services/ubicacion-service';
import { ArchivoService } from '../../../services/archivo-service';
import { ArchivoResponse } from '../../../models/archivo-response';

@Component({
  selector: 'app-mis-postulaciones',
  imports: [CommonModule],
  templateUrl: './mis-postulaciones.html',
  styleUrl: './mis-postulaciones.css',
})
export class MisPostulaciones implements OnInit {
  private postulacionService = inject(PostulacionService);
  private ubicacionService = inject(UbicacionService);
  private router = inject(Router);
  private archivoService = inject(ArchivoService);

  archivos = signal<ArchivoResponse[]>([]);
  postulaciones = signal<PostulacionResponse[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);
  postulacionSeleccionada = signal<PostulacionResponse | null>(null);

  nacionalidadNombre = signal('');
  provinciaNombre = signal('');
  localidadNombre = signal('');
  provinciaFamiliarNombre = signal('');
  localidadFamiliarNombre = signal('');

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

  async verDetalle(postulacion: PostulacionResponse): Promise<void> {
    this.limpiarUbicaciones();
    this.postulacionSeleccionada.set(postulacion);

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

    // Domicilio familiar
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

  tipoBeca(postulacion: PostulacionResponse): string {
    if (postulacion.becaBaseBis) return 'BASE/BIS';
    if (postulacion.becaBinid) return 'BINID';
    if (postulacion.becaCarreraInvestigador) return 'Carrera Investigador';
    return '—';
  }

  badgeEstado(estado: EstadoPostulacion): string {
    switch (estado) {
      case EstadoPostulacion.BORRADOR: return 'borrador';
      case EstadoPostulacion.PENDIENTE: return 'pendiente';
      case EstadoPostulacion.EN_REVISION: return 'revision';
      case EstadoPostulacion.ACEPTADO: return 'aceptado';
      case EstadoPostulacion.RECHAZADO: return 'rechazado';
      default: return '';
    }
  }

  volver(): void {
    this.router.navigate(['/alumno/dashboard']);
  }
}
