package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.CategoriaBinid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PostulacionBinidUnificadoRequest {

    @NotNull(message = "La convocatoria es obligatoria")
    private Long convocatoriaId;

    @NotNull(message = "Los datos personales son obligatorios")
    @Valid
    private DatosPersonalesRequest datosPersonales;

    @NotNull(message = "La categoria es obligatoria")
    private CategoriaBinid categoriaBinid;

    @NotBlank(message = "La carrera de grado es obligatoria")
    private String carreraGrado;

    @NotNull(message = "El año de ingreso es obligatorio")
    private Integer anioIngreso;

    private Integer anioEgreso;

    private Integer materiasCursadas;

    @NotNull(message = "El promedio con aplazos es obligatorio")
    private BigDecimal promedioConAplazos;

    private BigDecimal promedioSinAplazos;

    @NotBlank(message = "La pregunta es obligatoria")
    private String pregunta;

    @NotBlank(message = "El nombre del director es obligatorio")
    private String nombreDirectorProyecto;

    @NotBlank(message = "El apellido del director es obligatorio")
    private String apellidoDirectorProyecto;

    public PostulacionBinidUnificadoRequest() {}

    public Long getConvocatoriaId() { return convocatoriaId; }
    public void setConvocatoriaId(Long convocatoriaId) { this.convocatoriaId = convocatoriaId; }

    public DatosPersonalesRequest getDatosPersonales() { return datosPersonales; }
    public void setDatosPersonales(DatosPersonalesRequest datosPersonales) { this.datosPersonales = datosPersonales; }

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
    public void setApellidoDirectorProyecto(String apellidoDirectorProyecto) { this.apellidoDirectorProyecto = apellidoDirectorProyecto;}
}
