import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { ConvocatoriaRequest } from '../models/convocatoria-request';
import { Observable } from 'rxjs';
import { ConvocatoriaResponse } from '../models/convocatoria-response';
import { EstadoConvocatoria } from '../models/estado-convocatoria';
import { EstadisticasConvocatoriaResponse } from '../models/estadisticas-convocatoria-response';
import { InformeConvocatoriaResponse } from '../models/informe-convocatoria-response';

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

    listarAbiertas(): Observable<ConvocatoriaResponse[]> {
        return this.http.get<ConvocatoriaResponse[]>(`${this.API_URL}/abiertas`);
    }

    buscarPorId(id: number): Observable<ConvocatoriaResponse> {
        return this.http.get<ConvocatoriaResponse>(`${this.API_URL}/${id}`);
    }

    actualizar(id: number, request: ConvocatoriaRequest): Observable<ConvocatoriaResponse> {
        return this.http.put<ConvocatoriaResponse>(`${this.API_URL}/${id}`, request);
    }

    //Backend espera RequestParam por eso se manda null y se pasa estado como query param
    cambiarEstado(id: number, estado: EstadoConvocatoria): Observable<ConvocatoriaResponse> {
        return this.http.put<ConvocatoriaResponse>(
            `${this.API_URL}/${id}/estado`,
            null,
            {params: {estado}}
        )
    }

    obtenerEstadisticas(id: number): Observable<EstadisticasConvocatoriaResponse> {
        return this.http.get<EstadisticasConvocatoriaResponse>(`${this.API_URL}/${id}/estadisticas`);
    }

    subirInforme(id: number, file: File): Observable<InformeConvocatoriaResponse> {
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post<InformeConvocatoriaResponse>(`${this.API_URL}/${id}/informe`, formData);
    }

    obtenerInforme(id: number): Observable<InformeConvocatoriaResponse> {
        return this.http.get<InformeConvocatoriaResponse>(`${this.API_URL}/${id}/informe`);
    }

    //metodo eliminar convocatoria ???? informes, postulaciones asociadas...
}
