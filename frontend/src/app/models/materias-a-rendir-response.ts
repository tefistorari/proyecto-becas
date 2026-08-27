import { MesMesa } from "./mes-mesa";
import { NivelMateria } from "./nivel-materia";

export interface MateriasARendirResponse {
    id: number;
    nombreMateria: string;
    nivelMateria: NivelMateria;
    mesMesa: MesMesa;
    anioMesa: number;
}
