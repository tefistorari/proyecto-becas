import { HttpClient } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { AuthResponse } from '../models/auth-response';
import { AuthRequest } from '../models/auth-request';
import { Observable, tap } from 'rxjs';
import { Rol } from '../models/rol';

@Service()
export class AuthService {
    private http = inject(HttpClient);

    private readonly API_URL = 'http://localhost:8080/api/auth';

    //indica si existe una sesion iniciada
    private authenticated = signal(false); 

    //guarda informacion del usuario iniciado
    private currentUser = signal<AuthResponse | null>(null);

    //retorna pero no permite su modificacion
    isAuthenticated = this.authenticated.asReadonly();
    user = this.currentUser.asReadonly();

    constructor() {
        const token = this.getToken();
        const user = localStorage.getItem("user")

        if(token && user) {
            try {
                this.authenticated.set(true);
                this.currentUser.set(JSON.parse(user));
            } catch {
                //datos corruptos: limpiamos y arrancamos deslogueado
                localStorage.removeItem("token");
                localStorage.removeItem("user");
            }
        }
    }

    getToken(): string | null {
        return localStorage.getItem("token");
    }

    getCurrentUser(): AuthResponse | null {
        return this.currentUser();
    }

    hasRole(role: Rol): boolean {
        return this.currentUser()?.rol === role;
    }

    isAdmin(): boolean {
        return this.currentUser()?.rol === 'ADMINISTRADOR';
    }

    isAlumno(): boolean {
        return this.currentUser()?.rol === 'ALUMNO';
    }

    login(request: AuthRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(
            `${this.API_URL}/login`,
            request).pipe(
                tap(response => {
                    localStorage.setItem("token", response.token);
                    localStorage.setItem(
                        "user", 
                        JSON.stringify(response));

                    this.authenticated.set(true);
                    this.currentUser.set(response);
                })
            )
    }

    logout(): void {
        localStorage.removeItem("token");
        localStorage.removeItem("user");

        this.authenticated.set(false);
        this.currentUser.set(null);
    }
}
