import { CarreraBaseBis } from "./carrera-base-bis";
import { CondicionLaboral } from "./condicion-laboral";
import { DatosPersonalesRequest } from "./datos-personales-request";
import { GrupoFamiliarRequest } from "./grupo-familiar-request";
import { MateriasACursarRequest } from "./materias-a-cursar-request";
import { MateriasARendirRequest } from "./materias-a-rendir-request";
import { Salud } from "./salud";

export interface PostulacionBaseBisRequest {

    convocatoriaId: number;

    datosPersonales: DatosPersonalesRequest;

    tipoVivienda: string;
    condicionLaboral: CondicionLaboral;
    carrera: CarreraBaseBis,
    salud: Salud;
    tieneCondicionSalud: boolean;
    detalleCondicionSalud: string;

    grupoFamiliar: GrupoFamiliarRequest[];

    materiasACursar?: MateriasACursarRequest[];
    materiasARendir?: MateriasARendirRequest[];
}
