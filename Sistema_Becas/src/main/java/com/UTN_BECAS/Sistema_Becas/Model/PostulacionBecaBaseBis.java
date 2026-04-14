package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.CondicionLaboral;
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
    @NotNull(message = "La postulación es obligatoria")
    private Postulacion postulacion;

    @NotNull(message = "El ingreso familiar es obligatorio")
    @PositiveOrZero(message = "El ingreso familiar no puede ser negativo")
    private Double ingresoFamiliar;

    @NotBlank(message = "El tipo de vivienda es obligatorio")
    @Size(max = 500)
    private String tipoVivienda;

    @NotBlank(message = "La condicion laboral es obligatora")
    private CondicionLaboral condicionLaboral;

    @NotBlank(message = "La carrera es obligatoria")
    @Size(max = 150)
    private String carrera;

    @NotNull(message = "Debe indicar si tiene discapacidad")
    private Boolean tieneDiscapacidad;

    @Size(max = 4000, message = "El detalle de discapacidad no puede superar los 4000 caracteres")
    @Column(name = "detalle_discapacidad", length = 4000)
    private String detalleDiscapacidad;

    @AssertTrue(message = "Debe completar el detalle discapacidad si indicó que tiene una discapacidad")
    public boolean isDetalleValido () {
        if(Boolean.TRUE.equals(tieneDiscapacidad)) {
            return detalleDiscapacidad != null & !detalleDiscapacidad.isBlank();
        }
        return true;
    }

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
