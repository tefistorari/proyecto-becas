import { Rol } from "./rol";

export interface UsuarioResponse {
    id: number;
    nombre: string;
    apellido: string;
    email: string;
    rol: Rol;
}
