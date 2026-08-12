import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { BecaResponse } from '../models/beca-response';

@Service()
export class BecaService {
    private http = inject(HttpClient);
    private readonly API_URL = 'http://localhost:8080/api/becas';

    listarTodas(): Observable<BecaResponse[]> {
        return this.http.get<BecaResponse[]>(this.API_URL);
    }
    
}
