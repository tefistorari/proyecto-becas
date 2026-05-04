package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;

import java.time.LocalDateTime;
import java.util.List;

public class PostulacionResponse {

    private Long id;
    private LocalDateTime fechaEnvio;
    private EstadoPostulacion estado;
    private ConvocatoriaResponse convocatoria;
    private UsuarioResponse usuario;
    private DatosPersonalesHistorialResponse datosPersonalesHistorial;
    private List<GrupoFamiliarResponse> grupoFamiliar;
    private List<MateriasACursarResponse> materiasACursar;
    private List<MateriasARendirResponse> materiasARendir;

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

    public DatosPersonalesHistorialResponse getDatosPersonalesHistorial() { return datosPersonalesHistorial; }
    public void setDatosPersonalesHistorial(DatosPersonalesHistorialResponse datosPersonalesHistorial) { this.datosPersonalesHistorial = datosPersonalesHistorial; }

    public List<GrupoFamiliarResponse> getGrupoFamiliar() { return grupoFamiliar; }
    public void setGrupoFamiliar(List<GrupoFamiliarResponse> grupoFamiliar) { this.grupoFamiliar = grupoFamiliar; }

    public List<MateriasACursarResponse> getMateriasACursar() { return materiasACursar; }
    public void setMateriasACursar(List<MateriasACursarResponse> materiasACursar) { this.materiasACursar = materiasACursar; }

    public List<MateriasARendirResponse> getMateriasARendir() { return materiasARendir; }
    public void setMateriasARendir(List<MateriasARendirResponse> materiasARendir) { this.materiasARendir = materiasARendir; }
}
