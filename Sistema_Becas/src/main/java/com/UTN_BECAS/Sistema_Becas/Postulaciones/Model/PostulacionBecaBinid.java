package com.UTN_BECAS.Sistema_Becas.Postulaciones.Model;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraGrado;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CategoriaBinid;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "postulacion_beca_binid")
public class PostulacionBecaBinid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaBinid categoriaBinid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarreraGrado carreraGrado;

    @Column(name = "accedio_a_beca_binid_anterior", nullable = false)
    private boolean accedioABecaBinidAnterior = false;

    @Column(nullable = false)
    private Integer anioIngreso;

    /*Solo graduado*/
    private Integer anioEgreso;

    /*Si es estudiante*/
    private Integer materiasCursadas;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal promedioConAplazos;

    @Column(precision = 5, scale = 2)
    private BigDecimal promedioSinAplazos;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String pregunta;

    @Column(nullable = false)
    private String nombreDirectorProyecto;

    @Column(nullable = false)
    private String apellidoDirectorProyecto;

    public PostulacionBecaBinid() {
    }

    public PostulacionBecaBinid(Postulacion postulacion, CategoriaBinid categoriaBinid,
                                CarreraGrado carreraGrado, Integer anioIngreso, BigDecimal promedioConAplazos,
                                String pregunta, String nombreDirectorProyecto, String apellidoDirectorProyecto,
                                boolean accedioABecaBinidAnterior) {
        this.postulacion = postulacion;
        this.categoriaBinid = categoriaBinid;
        this.carreraGrado = carreraGrado;
        this.anioIngreso = anioIngreso;
        this.promedioConAplazos = promedioConAplazos;
        this.pregunta = pregunta;
        this.nombreDirectorProyecto = nombreDirectorProyecto;
        this.apellidoDirectorProyecto = apellidoDirectorProyecto;
        this.accedioABecaBinidAnterior = accedioABecaBinidAnterior;
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

    public CarreraGrado getCarreraGrado() {
        return carreraGrado;
    }

    public void setCarreraGrado(CarreraGrado carreraGrado) {
        this.carreraGrado = carreraGrado;
    }

    public boolean isAccedioABecaBinidAnterior() {
        return accedioABecaBinidAnterior;
    }

    public void setAccedioABecaBinidAnterior(boolean accedioABecaBinidAnterior) {
        this.accedioABecaBinidAnterior = accedioABecaBinidAnterior;
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

    public BigDecimal getPromedioConAplazos() {
        return promedioConAplazos;
    }

    public void setPromedioConAplazos(BigDecimal promedioConAplazos) {
        this.promedioConAplazos = promedioConAplazos;
    }

    public BigDecimal getPromedioSinAplazos() {
        return promedioSinAplazos;
    }

    public void setPromedioSinAplazos(BigDecimal promedioSinAplazos) {
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

    public String getApellidoDirectorProyecto() {
        return apellidoDirectorProyecto;
    }

    public void setApellidoDirectorProyecto(String apellidoDirectorProyecto) {
        this.apellidoDirectorProyecto = apellidoDirectorProyecto;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostulacionBecaBinid)) return false;
        PostulacionBecaBinid that = (PostulacionBecaBinid) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
