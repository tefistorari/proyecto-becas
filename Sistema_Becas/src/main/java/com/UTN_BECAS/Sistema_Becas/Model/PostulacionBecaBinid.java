package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.CategoriaBinid;
import jakarta.persistence.*;

@Entity
@Table(name = "postulacion_beca_binid")
public class PostulacionBecaBinid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaBinid categoriaBinid;

    @Column(nullable = false)
    private String carreraGrado;

    @Column(nullable = false)
    private Integer anioIngreso;

    /*Solo graduado*/
    private Integer anioEgreso;

    /*Si es estudiante*/
    private Integer materiasCursadas;

    @Column(nullable = false)
    private Double promedioConAplazos;

    private Double promedioSinAplazos;

    @Column(nullable = false)
    private String pregunta;

    @Column(nullable = false)
    private String nombreDirectorProyecto;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    public PostulacionBecaBinid() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaBinid getCategoriaBinid() {
        return categoriaBinid;
    }

    public void setCategoriaBinid(CategoriaBinid categoriaBinid) {
        this.categoriaBinid = categoriaBinid;
    }

    public String getCarreraGrado() {
        return carreraGrado;
    }

    public void setCarreraGrado(String carreraGrado) {
        this.carreraGrado = carreraGrado;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public Integer getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(Integer anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public Integer getMateriasCursadas() {
        return materiasCursadas;
    }

    public void setMateriasCursadas(Integer materiasCursadas) {
        this.materiasCursadas = materiasCursadas;
    }

    public Double getPromedioConAplazos() {
        return promedioConAplazos;
    }

    public void setPromedioConAplazos(Double promedioConAplazos) {
        this.promedioConAplazos = promedioConAplazos;
    }

    public Double getPromedioSinAplazos() {
        return promedioSinAplazos;
    }

    public void setPromedioSinAplazos(Double promedioSinAplazos) {
        this.promedioSinAplazos = promedioSinAplazos;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getNombreDirectorProyecto() {
        return nombreDirectorProyecto;
    }

    public void setNombreDirectorProyecto(String nombreDirectorProyecto) {
        this.nombreDirectorProyecto = nombreDirectorProyecto;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }
}
