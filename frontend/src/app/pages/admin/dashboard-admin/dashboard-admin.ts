import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../../services/auth-service';
import { FormsModule } from '@angular/forms';
import { PerfilService } from '../../../services/perfil-service';
import { UsuarioResponse } from '../../../models/usuario-response';

@Component({
  selector: 'app-dashboard-admin',
  imports: [RouterLink, FormsModule],
  templateUrl: './dashboard-admin.html',
  styleUrl: './dashboard-admin.css',
})
export class DashboardAdmin {
  protected authService = inject(AuthService);
  private perfilService = inject(PerfilService);
  private router = inject(Router);

  showLogoutModal = signal(false);

  // BUSCADOR DE ALUMNOS

  textoBusqueda = '';

  alumnosEncontrados = signal<UsuarioResponse[]>([]);

  buscando = signal(false);

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
    this.perfilService.buscarPorUsuarioId(alumno.id).subscribe({
      next: (perfil) => {
        console.log('Perfil del alumno:', perfil);
      },
      error: (error) => {
        console.error('Error al obtener el perfil:', error);
      }
    })
  }

  // CERRAR SESION

  confirmLogout(): void {
    this.showLogoutModal.set(true);
  }

  cancelLogout(): void {
    this.showLogoutModal.set(false);
  }

  logout(): void {
    this.showLogoutModal.set(false);
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
