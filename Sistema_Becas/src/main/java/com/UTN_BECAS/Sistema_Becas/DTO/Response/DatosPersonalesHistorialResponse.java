package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.Genero;

import java.time.LocalDate;

public class DatosPersonalesHistorialResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private String celular;
    private String domicilioCalle;
    private Integer domicilioNumero;
    private String domicilioPisoDepto;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String nacionalidad;

    public DatosPersonalesHistorialResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getDomicilioCalle() { return domicilioCalle; }
    public void setDomicilioCalle(String domicilioCalle) { this.domicilioCalle = domicilioCalle; }

    public Integer getDomicilioNumero() { return domicilioNumero; }
    public void setDomicilioNumero(Integer domicilioNumero) { this.domicilioNumero = domicilioNumero; }

    public String getDomicilioPisoDepto() { return domicilioPisoDepto; }
    public void setDomicilioPisoDepto(String domicilioPisoDepto) { this.domicilioPisoDepto = domicilioPisoDepto; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
}
