export enum CarreraBaseBis {
    //TECNICATURAS
    ADMINISTRACION = 'ADMINISTRACION',
    ENOLOGIA = 'ENOLOGIA',
    PROCEDIMIENTOS_TECNOLOGIAS_AMBIENTALES = 'PROCEDIMIENTOS_TECNOLOGIAS_AMBIENTALES',
    PRODUCCION_TEXTIL = 'PRODUCCION_TEXTIL',
    PROGRAMACION = 'PROGRAMACION',
    INTERIORISMO = 'INTERIORISMO',
    //INGENIERIAS
    INGENIERIA_NAVAL = 'INGENIERIA_NAVAL',
    INGENIERIA_PESQUERA = 'INGENIERIA_PESQUERA'
}

export const CarreraBaseBisDescripcion: Record<CarreraBaseBis, string> = {
    [CarreraBaseBis.ADMINISTRACION]: 'Administración',
    [CarreraBaseBis.ENOLOGIA]: 'Enología',
    [CarreraBaseBis.PROCEDIMIENTOS_TECNOLOGIAS_AMBIENTALES]: 'Procedimientos y Tecnologías Ambientales',
    [CarreraBaseBis.PRODUCCION_TEXTIL]: 'Producción Textil',
    [CarreraBaseBis.PROGRAMACION]: 'Programación',
    [CarreraBaseBis.INTERIORISMO]: 'Interiorismo',
    [CarreraBaseBis.INGENIERIA_NAVAL]: 'Ingeniería Naval',
    [CarreraBaseBis.INGENIERIA_PESQUERA]: 'Ingeniería Pesquera',
};