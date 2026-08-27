import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { ArchivoResponse } from '../models/archivo-response';

@Service()
export class ArchivoService {
    private http = inject(HttpClient);
    private readonly API_URL = 'http://localhost:8080/api/archivos';
    
    subir(postulacionId: number, formData: FormData): Observable<ArchivoResponse> {
        return this.http.post<ArchivoResponse>(`${this.API_URL}/${postulacionId}`, formData);
    }

    listarPorPostulacion(postulacionId: number): Observable<ArchivoResponse[]> {
        return this.http.get<ArchivoResponse[]>(`${this.API_URL}/${postulacionId}`);
    }

    eliminar(archivoId: number): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${archivoId}`);
    }
}
