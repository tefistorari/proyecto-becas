package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Enum.Salud;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PostulacionBecaBaseBisRequest {

    @NotNull(message = "El ingreso familiar es obligatorio")
    private BigDecimal ingresoFamiliar;

    @NotBlank(message = "El tipo de vivienda es obligatorio")
    private String tipoVivienda;

    @NotNull(message = "La condicion laboral es obligatoria")
    private CondicionLaboral condicionLaboral;

    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;

    @NotNull(message = "La salud es obligatoria")
    private Salud salud;

    @NotNull(message = "El campo tiene discapacidad es obligatorio")
    private Boolean tieneDiscapacidad;

    private String detalleDiscapacidad;

    public PostulacionBecaBaseBisRequest(){

    }

    public BigDecimal getIngresoFamiliar() {
        return ingresoFamiliar;
    }

    public void setIngresoFamiliar(BigDecimal ingresoFamiliar) {
        this.ingresoFamiliar = ingresoFamiliar;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public CondicionLaboral getCondicionLaboral() {
        return condicionLaboral;
    }

    public void setCondicionLaboral(CondicionLaboral condicionLaboral) {
        this.condicionLaboral = condicionLaboral;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Salud getSalud() {
        return salud;
    }

    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    public Boolean getTieneDiscapacidad() {
        return tieneDiscapacidad;
    }

    public void setTieneDiscapacidad(Boolean tieneDiscapacidad) {
        this.tieneDiscapacidad = tieneDiscapacidad;
    }

    public String getDetalleDiscapacidad() {
        return detalleDiscapacidad;
    }

    public void setDetalleDiscapacidad(String detalleDiscapacidad) {
        this.detalleDiscapacidad = detalleDiscapacidad;
    }
}
