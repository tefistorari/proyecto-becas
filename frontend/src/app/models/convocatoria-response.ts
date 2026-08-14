import { BecaResponse } from "./beca-response";
import { EstadoConvocatoria } from "./estado-convocatoria";
import { InformeConvocatoriaResponse } from "./informe-convocatoria-response";

export interface ConvocatoriaResponse {
    id: number;
    anio: number;
    fechaApertura: string;
    fechaCierre: string;
    descripcion?: string;
    estado: EstadoConvocatoria;
    beca: BecaResponse;
    cupoMaximo: number;
    informe?: InformeConvocatoriaResponse;
}
