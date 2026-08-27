import { NivelMateria } from "./nivel-materia";
import { RegimenMateria } from "./regimen-materia";

export interface MateriasACursarResponse {
    id: number;
    nombreMateria: string;
    nivelMateria: NivelMateria;
    regimenMateria: RegimenMateria;
    anioMateria: string;
}
