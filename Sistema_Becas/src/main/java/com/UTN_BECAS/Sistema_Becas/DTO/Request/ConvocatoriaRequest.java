package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class ConvocatoriaRequest {

    @NotNull(message = "La beca es obligatoria")
    private Long becaId;

    @NotNull(message = "El año es obligatorio")
    @Positive(message = "El año debe ser un numero positivo")
    private Integer anio;

    @NotNull(message = "La fecha de apertura es obligatoria")
    private LocalDateTime fechaApertura;

    @NotNull(message = "La fecha de cierre es obligatoria")
    private LocalDateTime fechaCierre;

    private String descripcion;

    public ConvocatoriaRequest(){

    }

    public Long getBecaId() {
        return becaId;
    }

    public void setBecaId(Long becaId) {
        this.becaId = becaId;
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
}
