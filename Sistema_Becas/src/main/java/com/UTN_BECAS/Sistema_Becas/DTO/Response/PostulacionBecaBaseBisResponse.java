package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Enum.Salud;

import java.math.BigDecimal;

public class PostulacionBecaBaseBisResponse {

    private BigDecimal ingresoFamiliar;
    private String tipoVivienda;
    private CondicionLaboral condicionLaboral;
    private String carrera;
    private Salud salud;
    private boolean tieneDiscapacidad;
    private String detalleDiscapacidad;

    public PostulacionBecaBaseBisResponse() {}

    public BigDecimal getIngresoFamiliar() { return ingresoFamiliar; }
    public void setIngresoFamiliar(BigDecimal ingresoFamiliar) { this.ingresoFamiliar = ingresoFamiliar; }

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }

    public CondicionLaboral getCondicionLaboral() { return condicionLaboral; }
    public void setCondicionLaboral(CondicionLaboral condicionLaboral) { this.condicionLaboral = condicionLaboral; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public Salud getSalud() { return salud; }
    public void setSalud(Salud salud) { this.salud = salud; }

    public boolean isTieneDiscapacidad() { return tieneDiscapacidad; }
    public void setTieneDiscapacidad(boolean tieneDiscapacidad) { this.tieneDiscapacidad = tieneDiscapacidad; }

    public String getDetalleDiscapacidad() { return detalleDiscapacidad; }
    public void setDetalleDiscapacidad(String detalleDiscapacidad) { this.detalleDiscapacidad = detalleDiscapacidad; }
}
