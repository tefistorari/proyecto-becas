import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { PostulacionBaseBisRequest } from '../models/postulacion-base-bis-request';
import { Observable } from 'rxjs';
import { PostulacionResponse } from '../models/postulacion-response';
import { PostulacionBinidRequest } from '../models/postulacion-binid-request';
import { PostulacionCarreraInvestigadorRequest } from '../models/postulacion-carrera-investigador-request';
import { PostulacionBinidResponse } from '../models/postulacion-binid-response';

@Service()
export class PostulacionService {
    private readonly API_URL = 'http://localhost:8080/api/postulaciones';
    private http = inject(HttpClient);

    postularBaseBis(request: PostulacionBaseBisRequest): Observable<PostulacionResponse> {
        return this.http.post<PostulacionResponse>(`${this.API_URL}/base-bis`,request);
    }

    postularBinid(request: PostulacionBinidRequest): Observable<PostulacionResponse> {
        return this.http.post<PostulacionResponse>(`${this.API_URL}/binid`,request);
    }

    postularCarreraInvestigador(request: PostulacionCarreraInvestigadorRequest): Observable<PostulacionResponse> {
        return this.http.post<PostulacionResponse>(`${this.API_URL}/carrera-investigador`, request);
    }

    finalizar(postulacionId: number): Observable<PostulacionResponse> {
        return this.http.post<PostulacionResponse>(`${this.API_URL}/${postulacionId}/finalizar`,{});
    }

    listarMisPostulaciones(): Observable<PostulacionResponse[]> {
        return this.http.get<PostulacionResponse[]>(`${this.API_URL}/mis-postulaciones`);
    }

    buscarPorId(id: number): Observable<PostulacionResponse> {
        return this.http.get<PostulacionResponse>(`${this.API_URL}/${id}`);
    }
}
