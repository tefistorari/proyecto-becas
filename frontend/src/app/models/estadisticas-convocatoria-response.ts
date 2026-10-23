import { EstadoPostulacion } from "./estado-postulacion";

export interface EstadisticasConvocatoriaResponse {
    convocatoriaId: number;
    nombreBeca: string;
    totalPostulaciones: number;
    porEstado: Record<EstadoPostulacion, number>;
}
