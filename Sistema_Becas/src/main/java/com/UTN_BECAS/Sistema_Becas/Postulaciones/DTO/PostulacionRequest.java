package com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO;

import jakarta.validation.constraints.NotNull;

public class PostulacionRequest {

    @NotNull(message = "La convocatoria es obligatoria")
    private Long convocatoriaId;

    public PostulacionRequest(){

    }

    public Long getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(Long convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }
}
