package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Enum.Salud;
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

    @Column(name = "ingreso_familiar", precision = 10, scale = 2)
    private BigDecimal ingresoFamiliar;

    @Column(name = "tipo_vivienda", nullable = false, length = 500)
    private String tipoVivienda;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_laboral", nullable = false)
    private CondicionLaboral condicionLaboral;

    @Column(nullable = false, length = 150)
    private String carrera;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Salud salud;

    @Column(name = "tiene_discapacidad", nullable = false)
    private boolean tieneDiscapacidad;

    @Column(name = "detalle_discapacidad", columnDefinition = "TEXT")
    private String detalleDiscapacidad;

    public PostulacionBecaBaseBis() {
    }

    public PostulacionBecaBaseBis(Postulacion postulacion, String tipoVivienda, CondicionLaboral condicionLaboral, String carrera, Salud salud, boolean tieneDiscapacidad) {
        this.postulacion = postulacion;
        this.tipoVivienda = tipoVivienda;
        this.condicionLaboral = condicionLaboral;
        this.carrera = carrera;
        this.salud = salud;
        this.tieneDiscapacidad = tieneDiscapacidad;
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

    public BigDecimal getIngresoFamiliar() {
        return ingresoFamiliar;
    }

    public void setIngresoFamiliar(BigDecimal ingresoFamiliar) {
        this.ingresoFamiliar = ingresoFamiliar;
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

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Salud getSalud() {
        return salud;
    }

    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    public boolean getTieneDiscapacidad() {
        return tieneDiscapacidad;
    }

    public void setTieneDiscapacidad(boolean tieneDiscapacidad) {
        this.tieneDiscapacidad = tieneDiscapacidad;
    }

    public String getDetalleDiscapacidad() {
        return detalleDiscapacidad;
    }

    public void setDetalleDiscapacidad(String detalleDiscapacidad) {
        this.detalleDiscapacidad = detalleDiscapacidad;
    }
}
