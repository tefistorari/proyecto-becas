import { Component, inject, OnInit, signal } from '@angular/core';
import { UbicacionService } from '../../services/ubicacion-service';

@Component({
  selector: 'app-ubicacion',
  imports: [],
  templateUrl: './ubicacion.html',
  styleUrl: './ubicacion.css',
})
export class Ubicacion implements OnInit {
  private ubicacionService = inject(UbicacionService);

  nacionalidades = signal<any[]>([]);
  provincias = signal<any[]>([]);
  localidades = signal<any[]>([]);

  ngOnInit(): void {
    this.cargarNacionalidades();
    this.cargarProvincias();
  }

  cargarNacionalidades(): void {
    this.ubicacionService.getNacionalidades()
          .then(nacionalidades => this.nacionalidades.set(nacionalidades));
  }

  cargarProvincias(): void {
    this.provincias.set([]);
    this.localidades.set([]);

    this.ubicacionService.getProvincias()
        .then(provincias => this.provincias.set(provincias));
  }

  cargarLocalidades(codigoProvincia: string): void {
    this.localidades.set([]);

    this.ubicacionService.getLocalidades(codigoProvincia)
        .then(localidades => this.localidades.set(localidades));
  }
}
