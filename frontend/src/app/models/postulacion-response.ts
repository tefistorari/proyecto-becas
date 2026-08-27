import { ConvocatoriaResponse } from "./convocatoria-response";
import { DatosPersonalesHistorialResponse } from "./datos-personales-historial-response";
import { EstadoPostulacion } from "./estado-postulacion";
import { GrupoFamiliarResponse } from "./grupo-familiar-response";
import { MateriasACursarResponse } from "./materias-a-cursar-response";
import { MateriasARendirResponse } from "./materias-a-rendir-response";
import { PostulacionBaseBisResponse } from "./postulacion-base-bis-response";
import { PostulacionBinidResponse } from "./postulacion-binid-response";
import { PostulacionCarreraInvestigadorResponse } from "./postulacion-carrera-investigador-response";
import { UsuarioResponse } from "./usuario-response";

export interface PostulacionResponse {
    id: number;
    fechaEnvio: string;
    estado: EstadoPostulacion;
    convocatoria: ConvocatoriaResponse;
    usuario: UsuarioResponse;

    becaBaseBis: PostulacionBaseBisResponse | null;
    becaBinid: PostulacionBinidResponse | null;
    becaCarreraInvestigador: PostulacionCarreraInvestigadorResponse | null;

    datosPersonalesHistorial: DatosPersonalesHistorialResponse;
    grupoFamiliar: GrupoFamiliarResponse[];
    materiasACursar: MateriasACursarResponse[];
    materiasARendir: MateriasARendirResponse[];
}
