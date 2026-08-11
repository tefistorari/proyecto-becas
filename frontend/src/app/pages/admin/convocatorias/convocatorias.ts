import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ConvocatoriaService } from '../../../services/convocatoria-service';
import { BecaService } from '../../../services/beca-service';
import { Router } from '@angular/router';
import { BecaResponse } from '../../../models/beca-response';
import { CommonModule } from '@angular/common';
import { ConvocatoriaResponse } from '../../../models/convocatoria-response';

@Component({
  selector: 'app-convocatorias',
  imports: [CommonModule],
  templateUrl: './convocatorias.html',
  styleUrl: './convocatorias.css',
})
export class Convocatorias implements OnInit {
  private convocatoriaService = inject(ConvocatoriaService);
  private router = inject(Router);

  convocatorias = signal<ConvocatoriaResponse[]>([]);
  cargando = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.convocatoriaService.listarTodas().subscribe({
      next: (data) => {
        this.convocatorias.set(data);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las convocatorias.');
        this.cargando.set(false);
      }
    });

  }

  nuevaConvocatoria(): void {
    this.router.navigate(['admin/convocatorias/nueva']);
  }
  
}
