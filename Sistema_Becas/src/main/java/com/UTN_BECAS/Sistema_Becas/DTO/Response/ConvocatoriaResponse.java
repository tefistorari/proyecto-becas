package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.EstadoConvocatoria;

import java.time.LocalDateTime;

public class ConvocatoriaResponse {

    private Long id;
    private Integer anio;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private String descripcion;
    private EstadoConvocatoria estado;
    private BecaResponse beca;

    public ConvocatoriaResponse(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoConvocatoria getEstado() {
        return estado;
    }

    public void setEstado(EstadoConvocatoria estado) {
        this.estado = estado;
    }

    public BecaResponse getBeca() {
        return beca;
    }

    public void setBeca(BecaResponse beca) {
        this.beca = beca;
    }
}
