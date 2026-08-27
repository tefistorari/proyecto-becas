import { CarreraGrado } from "./carrera-grado";
import { CategoriaBinid } from "./categoria-binid";
import { DatosPersonalesRequest } from "./datos-personales-request";

export interface PostulacionBinidRequest {

    convocatoriaId: number;

    datosPersonales: DatosPersonalesRequest;
    categoriaBinid: CategoriaBinid;
    carreraGrado: CarreraGrado;
    accedioABecaBinidAnterior: boolean;
    anioIngreso: number;

    /*Solo Graduado*/
    anioEgreso?: number;

    /*Solo Estudiante*/
    materiasCursadas?: number;

    promedioConAplazos: number;
    promedioSinAplazos?: number;
    pregunta: string;

    nombreDirectorProyecto: string;
    apellidoDirectorProyecto: string;
}
