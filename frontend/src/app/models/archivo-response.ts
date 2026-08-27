import { TipoDocumento } from "./tipo-documento";

export interface ArchivoResponse {
    id: number;
    nombre: string;
    tipoArchivo: TipoDocumento;
}
