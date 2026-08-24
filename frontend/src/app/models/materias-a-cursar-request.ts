import { NivelMateria } from "./nivel-materia";
import { RegimenMateria } from "./regimen-materia";

export interface MateriasACursarRequest {
    nombreMateria: string;
    nivelMateria: NivelMateria;
    regimenMateria: RegimenMateria;
    anioMateria: number;
}
