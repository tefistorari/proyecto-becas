import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../../services/auth-service';
import { FormsModule } from '@angular/forms';
import { PerfilService } from '../../../services/perfil-service';
import { UsuarioResponse } from '../../../models/usuario-response';
import { DatosPersonalesResponse } from '../../../models/datos-personales-response';
import { UbicacionService } from '../../../services/ubicacion-service';
import { LogoutButton } from "../../../components/logout-button/logout-button";

@Component({
  selector: 'app-dashboard-admin',
  imports: [RouterLink, FormsModule, LogoutButton],
  templateUrl: './dashboard-admin.html',
  styleUrl: './dashboard-admin.css',
})
export class DashboardAdmin {
  protected authService = inject(AuthService);
  private perfilService = inject(PerfilService);
  private ubicacionService = inject(UbicacionService);
  private router = inject(Router);

  showLogoutModal = signal(false);

  // BUSCADOR DE ALUMNOS
  textoBusqueda = '';

  alumnosEncontrados = signal<UsuarioResponse[]>([]);
  buscando = signal(false);
  perfilSeleccionado = signal<DatosPersonalesResponse | null>(null);
  alumnoSeleccionado = signal<UsuarioResponse | null>(null);

  nacionalidadNombre = signal('');
  provinciaNombre = signal('');
  localidadNombre = signal('');

  provinciaFamiliarNombre = signal('');
  localidadFamiliarNombre = signal('');

  buscarAlumnos(): void {
    
    const texto = this.textoBusqueda.trim();

    if(!texto) {
      this.alumnosEncontrados.set([]);
      return;
    }

    this.buscando.set(true);

    this.perfilService.buscarAlumnos(texto).subscribe({
      next: (alumnos) => {
        this.alumnosEncontrados.set(alumnos);
        this.buscando.set(false);
      },
      error: (error) => {
        console.error('Error al buscar alumnos:', error);
        this.alumnosEncontrados.set([]);
        this.buscando.set(false);
      }
    })
  }

  verPerfil(alumno: UsuarioResponse): void {
    
    this.nacionalidadNombre.set('');
    this.provinciaNombre.set('');
    this.localidadNombre.set('');
    this.provinciaFamiliarNombre.set('');
    this.localidadFamiliarNombre.set('');

    this.perfilService.buscarPorUsuarioId(alumno.id).subscribe({
      next: async (perfil) => {
        
        this.alumnoSeleccionado.set(alumno);
        this.perfilSeleccionado.set(perfil);

        const nacionalidades = await this.ubicacionService.getNacionalidades();
        const provincias = await this.ubicacionService.getProvincias();

        const nacionalidad = nacionalidades.find(
          n => n.iso2 === perfil.nacionalidad
        );
        const provincia = provincias.find(
          n => n.iso2 === perfil.provincia
        );

        this.nacionalidadNombre.set(nacionalidad?.name ?? perfil.nacionalidad);
        this.provinciaNombre.set(provincia?.name ?? perfil.provincia);

        if(perfil.provincia && perfil.localidad) {
          const localidades = await this.ubicacionService.getLocalidades(
            perfil.provincia
          );

          const localidad = localidades.find(
            l => String(l.id) === String(perfil.localidad)
          );
          this.localidadNombre.set(localidad?.name ?? String(perfil.localidad));
        }

        //domicilio familiar
        if(perfil.domicilioFamiliarDistinto && perfil.domicilioFamiliarProvincia) {
          const provinciaFamiliar = provincias.find(
            p => p.iso2 === perfil.domicilioFamiliarProvincia
          );

          this.provinciaFamiliarNombre.set(
            provinciaFamiliar?.name ?? perfil.domicilioFamiliarProvincia
          );

          if(perfil.domicilioFamiliarLocalidad) {
            const localidadesFamiliar = await this.ubicacionService.getLocalidades(
              perfil.domicilioFamiliarProvincia
            );

            const localidadFamiliar = localidadesFamiliar.find(
              l => String(l.id) === String(perfil.domicilioFamiliarLocalidad)
            );

            this.localidadFamiliarNombre.set(
              localidadFamiliar?.name ?? 
              String(perfil.domicilioFamiliarLocalidad)
            );
          }
        }
      },
      error:(error) => {
        console.error('Error al obtener el perfil:', error);
        if(error.status === 404) {
          this.alumnoSeleccionado.set(alumno);
          this.perfilSeleccionado.set(null);
        }
      }
    });
  }

  cerrarPerfil(): void {
    this.perfilSeleccionado.set(null);
    this.alumnoSeleccionado.set(null);

    this.nacionalidadNombre.set('');
    this.provinciaNombre.set('');
    this.localidadNombre.set('');
    this.provinciaFamiliarNombre.set('');
    this.localidadFamiliarNombre.set('');
  }

}
