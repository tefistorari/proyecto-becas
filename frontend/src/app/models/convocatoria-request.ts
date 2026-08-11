export interface ConvocatoriaRequest {
    becaId: number;
    anio: number;
    fechaApertura: string;
    fechaCierre: string;
    descripcion?: string;
    cupoMaximo: number;
}
