package com.UTN_BECAS.Sistema_Becas.Archivos.DTO;

import com.UTN_BECAS.Sistema_Becas.Archivos.Model.TipoArchivo;

import java.time.LocalDateTime;

public class ArchivoResponse {

    private Long id;
    private String nombreOriginal;
    private TipoArchivo tipoArchivo;
    private LocalDateTime fechaSubida;

    public ArchivoResponse(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(TipoArchivo tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
