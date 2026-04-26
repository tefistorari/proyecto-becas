package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.TipoBeca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BecaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El tipo de beca es obligatorio")
    private TipoBeca tipoBeca;

    @NotNull(message = "El campo requiere ingeniera es obligatorio")
    private Boolean requiereIngenieria;

    public BecaRequest(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoBeca getTipoBeca() {
        return tipoBeca;
    }

    public void setTipoBeca(TipoBeca tipoBeca) {
        this.tipoBeca = tipoBeca;
    }

    public Boolean getRequiereIngenieria() {
        return requiereIngenieria;
    }

    public void setRequiereIngenieria(Boolean requiereIngenieria) {
        this.requiereIngenieria = requiereIngenieria;
    }
}
