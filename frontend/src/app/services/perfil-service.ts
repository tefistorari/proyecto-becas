import { HttpClient } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { DatosPersonalesResponse } from '../models/datos-personales-response';
import { DatosPersonalesRequest } from '../models/datos-personales-request';
import { Observable, tap } from 'rxjs';
import { UsuarioResponse } from '../models/usuario-response';

@Service()
export class PerfilService {
    private http = inject(HttpClient);
    private readonly API_URL = 'http://localhost:8080/api/datos-personales';
    private readonly API_USUARIOS_URL = 'http://localhost:8080/api/usuarios';

    perfil  = signal<DatosPersonalesResponse | null>(null);

    //Rol ALUMNO
    crear(request: DatosPersonalesRequest): Observable<DatosPersonalesResponse> {
        return this.http.post<DatosPersonalesResponse>(this.API_URL, request)
            .pipe(tap(data => this.perfil.set(data)));
    }

    obtenerMisDatos(): Observable<DatosPersonalesResponse> {
        return this.http.get<DatosPersonalesResponse>(this.API_URL)
            .pipe(tap(data => this.perfil.set(data)));
    }

    actualizar(request: DatosPersonalesRequest): Observable<DatosPersonalesResponse> {
        return this.http.put<DatosPersonalesResponse>(this.API_URL, request)
            .pipe(tap(data => this.perfil.set(data)));
    }

    //Rol ADMIN
    buscarPorUsuarioId(usuarioId: number): Observable<DatosPersonalesResponse> {
        return this.http.get<DatosPersonalesResponse>(`${this.API_URL}/${usuarioId}`);
    }

    buscarAlumnos(texto: string): Observable<UsuarioResponse[]> {
        return this.http.get<UsuarioResponse[]>(
            `${this.API_USUARIOS_URL}/buscar?texto=${encodeURIComponent(texto)}`
        );
    }
}
