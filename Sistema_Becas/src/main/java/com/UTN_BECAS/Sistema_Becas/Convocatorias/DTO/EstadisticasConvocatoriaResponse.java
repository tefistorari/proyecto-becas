package com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO;

import java.util.Map;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;

public class EstadisticasConvocatoriaResponse {

    private Long convocatoriaId;
    private String nombreBeca;
    private Long totalPostulaciones;
    private Map<EstadoPostulacion, Long> porEstado;

    public Long getConvocatoriaId() { return convocatoriaId; }
    public void setConvocatoriaId(Long convocatoriaId) { this.convocatoriaId = convocatoriaId; }

    public String getNombreBeca() { return nombreBeca; }
    public void setNombreBeca(String nombreBeca) { this.nombreBeca = nombreBeca; }

    public Long getTotalPostulaciones() { return totalPostulaciones; }
    public void setTotalPostulaciones(Long totalPostulaciones) { this.totalPostulaciones = totalPostulaciones; }

    public Map<EstadoPostulacion, Long> getPorEstado() { return porEstado; }
    public void setPorEstado(Map<EstadoPostulacion, Long> porEstado) { this.porEstado = porEstado; }
}