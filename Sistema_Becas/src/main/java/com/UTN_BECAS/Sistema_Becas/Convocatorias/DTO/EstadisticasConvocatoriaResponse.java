package com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO;

public class EstadisticasConvocatoriaResponse {

    private Long convocatoriaId;
    private String nombreBeca;
    private Integer cupoMaximo;
    private Long totalInscriptos;
    private Long totalAceptados;
    private Double porcentajeOcupacion;

    public EstadisticasConvocatoriaResponse() {
    }

    public Long getConvocatoriaId() { return convocatoriaId; }
    public void setConvocatoriaId(Long convocatoriaId) { this.convocatoriaId = convocatoriaId; }

    public String getNombreBeca() { return nombreBeca; }
    public void setNombreBeca(String nombreBeca) { this.nombreBeca = nombreBeca; }

    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public Long getTotalInscriptos() { return totalInscriptos; }
    public void setTotalInscriptos(Long totalInscriptos) { this.totalInscriptos = totalInscriptos; }

    public Long getTotalAceptados() { return totalAceptados; }
    public void setTotalAceptados(Long totalAceptados) { this.totalAceptados = totalAceptados; }

    public Double getPorcentajeOcupacion() { return porcentajeOcupacion; }
    public void setPorcentajeOcupacion(Double porcentajeOcupacion) { this.porcentajeOcupacion = porcentajeOcupacion; }
}