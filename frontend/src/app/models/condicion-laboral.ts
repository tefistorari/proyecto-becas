export enum CondicionLaboral {
    OCUPADO = 'OCUPADO',
    DESOCUPADO = 'DESOCUPADO',
    SUB_OCUPADO = 'SUB_OCUPADO'
}

export const CondicionLaboralDescripcion: Record<CondicionLaboral, string> = {
    [CondicionLaboral.OCUPADO]: 'Ocupado',
    [CondicionLaboral.DESOCUPADO]: 'Desocupado',
    [CondicionLaboral.SUB_OCUPADO]: 'Subocupado'
};
