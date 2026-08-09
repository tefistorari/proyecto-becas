package com.UTN_BECAS.Sistema_Becas.Postulaciones.Model;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraBaseBis;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.Salud;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "postulacion_beca_base_bis")
public class PostulacionBecaBaseBis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    @Column(name = "tipo_vivienda", nullable = false, length = 500)
    private String tipoVivienda;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_laboral", nullable = false)
    private CondicionLaboral condicionLaboral;

    @Column(nullable = false, length = 150)
    private CarreraBaseBis carrera;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Salud salud;

    @Column(name = "tiene_condicion_salud", nullable = false)
    private boolean tieneCondicionSalud;

    @Column(name = "detalle_condicion_salud", columnDefinition = "TEXT")
    private String detalleCondicionSalud;

    public PostulacionBecaBaseBis() {
    }

    public PostulacionBecaBaseBis(Postulacion postulacion, String tipoVivienda, CondicionLaboral condicionLaboral, CarreraBaseBis carrera, Salud salud, boolean tieneCondicionSalud) {
        this.postulacion = postulacion;
        this.tipoVivienda = tipoVivienda;
        this.condicionLaboral = condicionLaboral;
        this.carrera = carrera;
        this.salud = salud;
        this.tieneCondicionSalud = tieneCondicionSalud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public CondicionLaboral getCondicionLaboral() {
        return condicionLaboral;
    }

    public void setCondicionLaboral(CondicionLaboral condicionLaboral) {
        this.condicionLaboral = condicionLaboral;
    }

    public CarreraBaseBis getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraBaseBis carrera) {
        this.carrera = carrera;
    }

    public Salud getSalud() {
        return salud;
    }

    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    public String getDetalleCondicionSalud() {
        return detalleCondicionSalud;
    }

    public void setDetalleCondicionSalud(String detalleCondicionSalud) {
        this.detalleCondicionSalud = detalleCondicionSalud;
    }

    public boolean isTieneCondicionSalud() {
        return tieneCondicionSalud;
    }

    public void setTieneCondicionSalud(boolean tieneCondicionSalud) {
        this.tieneCondicionSalud = tieneCondicionSalud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostulacionBecaBaseBis)) return false;
        PostulacionBecaBaseBis that = (PostulacionBecaBaseBis) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
