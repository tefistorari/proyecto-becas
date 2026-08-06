import { Rol } from "./rol";

export interface AuthResponse {
    token: string;
    nombre: string;
    apellido: string;
    email: string;
    rol: Rol;
}
