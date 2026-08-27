import { CarreraGrado } from "./carrera-grado";
import { CategoriaBinid } from "./categoria-binid";

export interface PostulacionBinidResponse {
    categoriaBinid: CategoriaBinid;
    carreraGrado: CarreraGrado;
    anioIngreso: number;
    anioEgreso?: number;
    materiasCursadas?: number;
    promedioConAplazos: number;
    promedioSinAplazos?: number;
    pregunta: string;
    nombreDirectorProyecto: string;
    apellidoDirectorProyecto: string;
    accedioABecaBinidAnterior: boolean;
}
