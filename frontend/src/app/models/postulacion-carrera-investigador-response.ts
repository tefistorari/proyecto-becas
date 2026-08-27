import { CarreraGrado } from "./carrera-grado";
import { CategoriaInvestigador } from "./categoria-investigador";

export interface PostulacionCarreraInvestigadorResponse {
    categoriaActual?: CategoriaInvestigador;
    categoriaSolicitada: CategoriaInvestigador;
    materia: string;
    carreraGrado: CarreraGrado;
}
