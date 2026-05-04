package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Enum.Salud;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class PostulacionBaseBisUnificadoRequest {

    @NotNull(message = "La convoncatoria es obligatoria")
    private Long convocatoriaId;

    //Datos personales historial
    @NotNull(message = "Los datos personales son obligatorios")
    @Valid
    private DatosPersonalesRequest datosPersonales;

    //Datos especificos de BASE/BIS
    @NotNull(message = "El ingreso familiar es obligatorio")
    private BigDecimal ingresoFamiliar;

    @NotBlank(message = "El tipo de vivienda es obligatorio")
    private String tipoVivienda;

    @NotNull(message = "La condicion laboral es obligatoria")
    private CondicionLaboral condicionLaboral;

    @NotBlank(message = "La carrera  es obligatoria")
    private String carrera;

    @NotNull(message = "La salud es obligatoria")
    private Salud salud;

    @NotNull(message = "El campo tiene discapacidad es obligatorio")
    private Boolean tieneDiscapacidad;

    private String detalleDiscapacidad;

    //Listas
    @NotNull(message = "El grupo familiar es obligatorio")
    @Valid
    private List<GrupoFamiliarRequest> grupoFamiliar;

    @Valid
    private List<MateriasACursarRequest> materiasACursar;

    @Valid
    private List<MateriasARendirRequest> materiasARendir;

    public PostulacionBaseBisUnificadoRequest() {
    }

    //getters y setters


    public Long getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(Long convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }

    public DatosPersonalesRequest getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(DatosPersonalesRequest datosPersonales) {
        this.datosPersonales = datosPersonales;
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

    public List<GrupoFamiliarRequest> getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public void setGrupoFamiliar(List<GrupoFamiliarRequest> grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }

    public List<MateriasACursarRequest> getMateriasACursar() {
        return materiasACursar;
    }

    public void setMateriasACursar(List<MateriasACursarRequest> materiasACursar) {
        this.materiasACursar = materiasACursar;
    }

    public List<MateriasARendirRequest> getMateriasARendir() {
        return materiasARendir;
    }

    public void setMateriasARendir(List<MateriasARendirRequest> materiasARendir) {
        this.materiasARendir = materiasARendir;
    }
}
