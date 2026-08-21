import { Parentesco } from "./parentesco";

export interface GrupoFamiliarRequest {
    nombre: string;
    apellido: string;
    dni: string;
    parentesco: Parentesco;
    ocupacion: string;
    ingreso: number;
}
