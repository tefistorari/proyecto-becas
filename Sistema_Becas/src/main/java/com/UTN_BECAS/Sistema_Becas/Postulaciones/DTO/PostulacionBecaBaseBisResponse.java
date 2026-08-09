package com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraBaseBis;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.Salud;

import java.math.BigDecimal;

public class PostulacionBecaBaseBisResponse {

    private String tipoVivienda;
    private CondicionLaboral condicionLaboral;
    private CarreraBaseBis carrera;
    private Salud salud;
    private boolean tieneCondicionSalud;
    private String detalleCondicionSalud;

    public PostulacionBecaBaseBisResponse() {}

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }

    public CondicionLaboral getCondicionLaboral() { return condicionLaboral; }
    public void setCondicionLaboral(CondicionLaboral condicionLaboral) { this.condicionLaboral = condicionLaboral; }

    public CarreraBaseBis getCarrera() { return carrera; }
    public void setCarrera(CarreraBaseBis carrera) { this.carrera = carrera; }

    public Salud getSalud() { return salud; }
    public void setSalud(Salud salud) { this.salud = salud; }

    public boolean isTieneCondicionSalud() {
        return tieneCondicionSalud;
    }

    public void setTieneCondicionSalud(boolean tieneCondicionSalud) {
        this.tieneCondicionSalud = tieneCondicionSalud;
    }

    public String getDetalleCondicionSalud() {
        return detalleCondicionSalud;
    }

    public void setDetalleCondicionSalud(String detalleCondicionSalud) {
        this.detalleCondicionSalud = detalleCondicionSalud;
    }
}
