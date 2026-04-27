package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;

import java.time.LocalDateTime;

public class PostulacionResponse {

    private Long id;
    private LocalDateTime fechaEnvio;
    private EstadoPostulacion estado;
    private ConvocatoriaResponse convocatoria;
    private UsuarioResponse usuario;

    public PostulacionResponse(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }

    public ConvocatoriaResponse getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(ConvocatoriaResponse convocatoria) {
        this.convocatoria = convocatoria;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }
}
