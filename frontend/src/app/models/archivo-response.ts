import { TipoDocumento } from "./tipo-documento";

export interface ArchivoResponse {
    id: number;
    nombreOriginal: string;
    tipoArchivo: TipoDocumento;
    fechaSubida: string;
}
