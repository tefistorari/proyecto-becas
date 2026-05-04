package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.NivelMateria;
import com.UTN_BECAS.Sistema_Becas.Enum.RegimenMateria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MateriasACursarRequest {

    @NotBlank(message = "El nombre de la materia es obligatorio")
    private String nombreMateria;

    @NotNull(message = "El nivel es obligatorio")
    private NivelMateria nivelMateria;

    @NotNull(message = "El regimen es obigatorio")
    private RegimenMateria regimenMateria;

    @NotNull(message = "El anio es obligatorio")
    private Integer anioMateria;

    public MateriasACursarRequest() {
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public NivelMateria getNivelMateria() {
        return nivelMateria;
    }

    public void setNivelMateria(NivelMateria nivelMateria) {
        this.nivelMateria = nivelMateria;
    }

    public RegimenMateria getRegimenMateria() {
        return regimenMateria;
    }

    public void setRegimenMateria(RegimenMateria regimenMateria) {
        this.regimenMateria = regimenMateria;
    }

    public Integer getAnioMateria() {
        return anioMateria;
    }

    public void setAnioMateria(Integer anioMateria) {
        this.anioMateria = anioMateria;
    }
}
