import { CarreraGrado } from "./carrera-grado";
import { CategoriaInvestigador } from "./categoria-investigador";
import { DatosPersonalesRequest } from "./datos-personales-request";

export interface PostulacionCarreraInvestigadorRequest {
    convocatoriaId: number;
    datosPersonales: DatosPersonalesRequest;
    categoriaActual?: CategoriaInvestigador;
    categoriaSolicitada: CategoriaInvestigador;
    materia: string;
    carreraGrado: CarreraGrado;
}
