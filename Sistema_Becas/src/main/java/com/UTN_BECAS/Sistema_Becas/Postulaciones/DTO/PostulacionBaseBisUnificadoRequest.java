package com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraBaseBis;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.Salud;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.GrupoFamiliarRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.MateriasACursarRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.MateriasARendirRequest;
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
    @NotBlank(message = "El tipo de vivienda es obligatorio")
    private String tipoVivienda;

    @NotNull(message = "La condicion laboral es obligatoria")
    private CondicionLaboral condicionLaboral;

    @NotNull(message = "La carrera de grado es obligatoria")
    private CarreraBaseBis carrera;

    @NotNull(message = "La salud es obligatoria")
    private Salud salud;

    @NotNull(message = "El campo tiene condicion de salud es obligatorio")
    private Boolean tieneCondicionSalud;

    private String detalleCondicionSalud;

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

    public CarreraBaseBis getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraBaseBis carrera) {
        this.carrera = carrera;
    }

    public Salud getSalud() {
        return salud;
    }

    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    public Boolean getTieneCondicionSalud() {
        return tieneCondicionSalud;
    }

    public void setTieneCondicionSalud(Boolean tieneCondicionSalud) {
        this.tieneCondicionSalud = tieneCondicionSalud;
    }

    public String getDetalleCondicionSalud() {
        return detalleCondicionSalud;
    }

    public void setDetalleCondicionSalud(String detalleCondicionSalud) {
        this.detalleCondicionSalud = detalleCondicionSalud;
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
