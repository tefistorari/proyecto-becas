package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.MesMesa;
import com.UTN_BECAS.Sistema_Becas.Enum.NivelMateria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MateriasARendirRequest {

    @NotBlank(message = "El nombre de la materia es obligatorio")
    private String nombreMateria;

    @NotNull(message = "El nivel es obligatorio")
    private NivelMateria nivelMateria;

    @NotNull(message = "El mes de la mesa es obligatorio")
    private MesMesa mesMesa;

    @NotNull(message = "El año de la mesa es obligatorio")
    private Integer anioMesa;

    public MateriasARendirRequest() {
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

    public MesMesa getMesMesa() {
        return mesMesa;
    }

    public void setMesMesa(MesMesa mesMesa) {
        this.mesMesa = mesMesa;
    }

    public Integer getAnioMesa() {
        return anioMesa;
    }

    public void setAnioMesa(Integer anioMesa) {
        this.anioMesa = anioMesa;
    }
}
