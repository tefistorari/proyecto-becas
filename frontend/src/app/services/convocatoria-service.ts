import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { ConvocatoriaRequest } from '../models/convocatoria-request';
import { Observable } from 'rxjs';
import { ConvocatoriaResponse } from '../models/convocatoria-response';
import { EstadoConvocatoria } from '../models/estado-convocatoria';

@Service()
export class ConvocatoriaService {
    private http = inject(HttpClient);
    private readonly API_URL = 'http://localhost:8080/api/convocatorias';

    crear(request: ConvocatoriaRequest): Observable<ConvocatoriaResponse> {
        return this.http.post<ConvocatoriaResponse>(this.API_URL, request);
    }

    listarTodas(): Observable<ConvocatoriaResponse[]> {
        return this.http.get<ConvocatoriaResponse[]>(this.API_URL);
    }

    buscarPorId(id: number): Observable<ConvocatoriaResponse> {
        return this.http.get<ConvocatoriaResponse>(`${this.API_URL}/${id}`);
    }

    //Backend espera RequestParam por eso se manda null y se pasa estado como query param
    cambiarEstado(id: number, estado: EstadoConvocatoria): Observable<ConvocatoriaResponse> {
        return this.http.put<ConvocatoriaResponse>(
            `${this.API_URL}/${id}/estado`,
            null,
            {params: {estado}}
        )
    }
}
