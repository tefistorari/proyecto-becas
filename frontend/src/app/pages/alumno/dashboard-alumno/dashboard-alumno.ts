import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../services/auth-service';
import { Router, RouterLink } from '@angular/router';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { ConvocatoriaResponse } from '../../../models/convocatoria-response';
import { DatePipe } from '@angular/common';
import { TipoBeca } from '../../../models/tipo-beca';
import { LogoutButton } from "../../../components/logout-button/logout-button";

@Component({
  selector: 'app-dashboard-alumno',
  imports: [RouterLink, DatePipe, LogoutButton],
  templateUrl: './dashboard-alumno.html',
  styleUrl: './dashboard-alumno.css',
})
export class DashboardAlumno implements OnInit{
  protected authService = inject(AuthService);

  private router = inject(Router);
  private convocatoriaService = inject(ConvocatoriaService);

  convocatoriasAbiertas = signal<ConvocatoriaResponse[]>([]);

  showLogoutModal = signal(false);

  ngOnInit(): void {
    this.cargarConvocatoriasAbiertas();
  }

  cargarConvocatoriasAbiertas(): void {

    this.convocatoriaService.listarAbiertas()
      .subscribe({
        next: (convocatorias) => {
          this.convocatoriasAbiertas.set(convocatorias);
        },
        error: (error) => {
          console.error(
            'Error al cargar las convocatorias abiertas:',
            error
          );
        }
      });
  }

  irAFormulario(convocatoria: ConvocatoriaResponse): void {
    switch(convocatoria.beca.tipoBeca) {

      case TipoBeca.BASE:
        this.router.navigate(['/alumno/postular/base', convocatoria.id]);
        break;

      case TipoBeca.BIS:
        this.router.navigate(['alumno/postular/bis', convocatoria.id]);
        break;

      case TipoBeca.BINID:
        this.router.navigate(['alumno/postular/binid', convocatoria.id]);
        break;
      case TipoBeca.CARRERA_INVESTIGADOR:
        this.router.navigate(['alumno/postular/carrera-investigador', convocatoria.id]);
        break;
    }
  }

}
