package com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO;

import java.util.List;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;

public record ReporteConvocatoriaResponse(
    String nombreBeca,
    Integer anio,
    long total,
    long aprobados,
    long rechazados,
    List<Postulado> postulados
) {
    public record Postulado(
            String apellido,
            String nombre,
            String dni,
            EstadoPostulacion estado) {
    }
}
