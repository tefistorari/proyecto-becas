package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.TipoBeca;

public class BecaResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private TipoBeca tipoBeca;
    private boolean requiereIngenieria;

    public BecaResponse(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isRequiereIngenieria() {
        return requiereIngenieria;
    }

    public void setRequiereIngenieria(boolean requiereIngenieria) {
        this.requiereIngenieria = requiereIngenieria;
    }
}
