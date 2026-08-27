import { CarreraBaseBis } from "./carrera-base-bis";
import { CondicionLaboral } from "./condicion-laboral";
import { Salud } from "./salud";

export interface PostulacionBaseBisResponse {
    tipoVivienda: string;
    condicionLaboral: CondicionLaboral;
    carrera: CarreraBaseBis;
    salud: Salud;
    tieneCondicionSalud: boolean;
    detalleCondicionSalud?: string;
}
