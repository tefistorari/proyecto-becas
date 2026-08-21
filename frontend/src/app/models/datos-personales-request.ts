import { Genero } from "./genero";

export interface DatosPersonalesRequest {
    dni: string;
    fechaNacimiento: string;
    genero: Genero;
    celular: string;

    domicilioCalle: string;
    domiciloNumero: number;
    domicilioPisoDepto?: string;

    codigoPostal: string;
    localidad: string;
    provincia: string;
    nacionalidad: string;

    domicilioFamiliarDistinto: boolean;

    domicilioFamiliarCalle?: string;
    domicilioFamiliarNumero?: number;
    domicilioFamiliarPisoDepto?: string;
    domicilioFamiliarCodigoPostal?: string;
    domicilioFamiliarLocalidad?: string;
    domicilioFamiliarProvincia?: string;
}
