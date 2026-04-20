package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.CondicionLaboral;
import com.UTN_BECAS.Sistema_Becas.Enum.Salud;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.awt.*;

@Entity
@Table(name = "postulacion_beca_base_bis")
public class PostulacionBecaBaseBis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    @PositiveOrZero(message = "El ingreso familiar no puede ser negativo")
    private Double ingresoFamiliar;

    @NotBlank(message = "El tipo de vivienda es obligatorio")
    @Size(max = 500)
    private String tipoVivienda;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CondicionLaboral condicionLaboral;

    @NotBlank(message = "La carrera es obligatoria")
    @Size(max = 150)
    private String carrera;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Salud salud;

    private Boolean tieneDiscapacidad;

    @Size(max = 4000, message = "El detalle de discapacidad no puede superar los 4000 caracteres")
    @Column(name = "detalle_discapacidad", length = 4000)
    private String detalleDiscapacidad;

    public PostulacionBecaBaseBis() {
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

    public Double getIngresoFamiliar() {
        return ingresoFamiliar;
    }

    public void setIngresoFamiliar(Double ingresoFamiliar) {
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

    public Salud getSalud() {
        return salud;
    }

    public void setSalud(Salud salud) {
        this.salud = salud;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Boolean getTieneDiscapacidad() {
        return tieneDiscapacidad;
    }

    public void setTieneDiscapacidad(Boolean tieneDiscapacidad) {
        this.tieneDiscapacidad = tieneDiscapacidad;
    }

    public String getDetalleDiscapacidad() {
        return detalleDiscapacidad;
    }

    public void setDetalleDiscapacidad(String detalleDiscapacidad) {
        this.detalleDiscapacidad = detalleDiscapacidad;
    }
}
