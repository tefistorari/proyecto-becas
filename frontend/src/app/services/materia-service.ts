import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Materia } from '../models/Materia';


@Injectable({ providedIn: 'root' })
export class MateriaService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/materias';

  listarPorCarrera(carrera: string): Observable<Materia[]> {
    return this.http.get<Materia[]>(this.API_URL, { params: { carrera } });
  }
}