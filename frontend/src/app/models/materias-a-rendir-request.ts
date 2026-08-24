import { MesMesa } from "./mes-mesa";
import { NivelMateria } from "./nivel-materia";

export interface MateriasARendirRequest {
    nombreMateria: string;
    nivelMateria: NivelMateria;
    mesMesa: MesMesa;
    anioMesa: number;
}
