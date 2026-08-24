import { Genero } from "./genero";

export interface DatosPersonalesResponse {
    id: number;
    dni: string;
    fechaNacimiento: string;
    genero: Genero;
    celular: string;

    domicilioCalle: string;
    domicilioNumero: number;
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
