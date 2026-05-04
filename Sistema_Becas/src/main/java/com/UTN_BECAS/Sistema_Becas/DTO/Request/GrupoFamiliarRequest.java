package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.Parentesco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class GrupoFamiliarRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotNull(message = "El parentesco es obligatorio")
    private Parentesco parentesco;

    @NotNull(message = "La ocupacion es obligatoria")
    private String ocupacion;

    @NotBlank(message = "El ingreso es obligatorio")
    private BigDecimal ingreso;

    public GrupoFamiliarRequest() {
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
}
