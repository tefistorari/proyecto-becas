import { Genero } from "./genero";

export interface DatosPersonalesHistorialResponse {
    id: number;
    nombre: string;
    apellido: string;
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
