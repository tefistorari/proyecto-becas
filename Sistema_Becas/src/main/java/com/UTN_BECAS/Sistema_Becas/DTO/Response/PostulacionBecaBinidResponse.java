package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.CategoriaBinid;

import java.math.BigDecimal;

public class PostulacionBecaBinidResponse {

    private CategoriaBinid categoriaBinid;
    private String carreraGrado;
    private Integer anioIngreso;
    private Integer anioEgreso;
    private Integer materiasCursadas;
    private BigDecimal promedioConAplazos;
    private BigDecimal promedioSinAplazos;
    private String pregunta;
    private String nombreDirectorProyecto;
    private String apellidoDirectorProyecto;

    public PostulacionBecaBinidResponse() {}

    public CategoriaBinid getCategoriaBinid() { return categoriaBinid; }
    public void setCategoriaBinid(CategoriaBinid categoriaBinid) { this.categoriaBinid = categoriaBinid; }

    public String getCarreraGrado() { return carreraGrado; }
    public void setCarreraGrado(String carreraGrado) { this.carreraGrado = carreraGrado; }

    public Integer getAnioIngreso() { return anioIngreso; }
    public void setAnioIngreso(Integer anioIngreso) { this.anioIngreso = anioIngreso; }

    public Integer getAnioEgreso() { return anioEgreso; }
    public void setAnioEgreso(Integer anioEgreso) { this.anioEgreso = anioEgreso; }

    public Integer getMateriasCursadas() { return materiasCursadas; }
    public void setMateriasCursadas(Integer materiasCursadas) { this.materiasCursadas = materiasCursadas; }

    public BigDecimal getPromedioConAplazos() { return promedioConAplazos; }
    public void setPromedioConAplazos(BigDecimal promedioConAplazos) { this.promedioConAplazos = promedioConAplazos; }

    public BigDecimal getPromedioSinAplazos() { return promedioSinAplazos; }
    public void setPromedioSinAplazos(BigDecimal promedioSinAplazos) { this.promedioSinAplazos = promedioSinAplazos; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getNombreDirectorProyecto() { return nombreDirectorProyecto; }
    public void setNombreDirectorProyecto(String nombreDirectorProyecto) { this.nombreDirectorProyecto = nombreDirectorProyecto; }

    public String getApellidoDirectorProyecto() { return apellidoDirectorProyecto; }
    public void setApellidoDirectorProyecto(String apellidoDirectorProyecto) { this.apellidoDirectorProyecto = apellidoDirectorProyecto; }
}