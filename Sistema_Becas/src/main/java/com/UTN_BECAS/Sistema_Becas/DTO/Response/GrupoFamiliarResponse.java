package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.Parentesco;

import java.math.BigDecimal;

public class GrupoFamiliarResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private Parentesco parentesco;
    private String ocupacion;
    private BigDecimal ingreso;

    public GrupoFamiliarResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public Parentesco getParentesco() { return parentesco; }
    public void setParentesco(Parentesco parentesco) { this.parentesco = parentesco; }

    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }

    public BigDecimal getIngreso() { return ingreso; }
    public void setIngreso(BigDecimal ingreso) { this.ingreso = ingreso; }
}
