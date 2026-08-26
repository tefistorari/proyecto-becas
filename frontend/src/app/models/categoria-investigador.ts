export enum CategoriaInvestigador {
    D = 'D',
    E = 'E',
    F = 'F',
    G = 'G'
}

export const CategoriaInvestigadorDescripcion: Record<CategoriaInvestigador, string> = {
    [CategoriaInvestigador.D]: 'D - Docente con mínimo 4 años en proyectos homologados o con tesis de Maestría/Doctorado',
    [CategoriaInvestigador.E]: 'E - Docente con mínimo 2 años de antigüedad en proyectos homologados',
    [CategoriaInvestigador.F]: 'F - Docente con participación en proyectos de investigación',
    [CategoriaInvestigador.G]: 'G - Nivel inicial - Graduado o alumno avanzado con vocación investigadora'
};
