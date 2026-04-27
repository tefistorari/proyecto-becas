package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.Parentesco;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "grupo_familiar",
        uniqueConstraints = @UniqueConstraint(columnNames = {"postulacion_id", "dni"}))
public class GrupoFamiliar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String dni;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Parentesco parentesco;

    @Column(nullable = false)
    private String ocupacion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ingreso;

    @ManyToOne
    @JoinColumn(name = "postulacion_id", nullable = false)
    private Postulacion postulacion;

    public GrupoFamiliar() {
    }

    public GrupoFamiliar(String nombre, String apellido, String dni, Parentesco parentesco, String ocupacion, BigDecimal ingreso, Postulacion postulacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.parentesco = parentesco;
        this.ocupacion = ocupacion;
        this.ingreso = ingreso;
        this.postulacion = postulacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }
}
