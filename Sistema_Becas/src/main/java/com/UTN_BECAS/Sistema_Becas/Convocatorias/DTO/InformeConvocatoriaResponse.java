package com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO;

import java.time.LocalDateTime;

public class InformeConvocatoriaResponse {

    private Long id;
    private String nombreOriginal;
    private LocalDateTime fechaSubida;

    public InformeConvocatoriaResponse() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }

    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }
}