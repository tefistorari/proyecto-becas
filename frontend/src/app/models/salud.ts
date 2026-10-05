export enum Salud {
    CON_OBRASOCIAL = 'CON_OBRASOCIAL',
    SIN_OBRASOCIAL = 'SIN_OBRASOCIAL',
    COBERTURA_PARCIAL = 'COBERTURA_PARCIAL'
}

export const SaludDescripcion: Record<Salud, string>= {
   [Salud.CON_OBRASOCIAL]: 'Con obra social',
    [Salud.SIN_OBRASOCIAL]: 'Sin obra social',
    [Salud.COBERTURA_PARCIAL]: 'Cobertura parcial'
}
