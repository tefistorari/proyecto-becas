import { TipoBeca } from "./tipo-beca";

export interface BecaResponse {
    id: number;
    nombre: string;
    tipoBeca: TipoBeca;
    descripcion: string;
    requiereIngenieria: boolean;
}
