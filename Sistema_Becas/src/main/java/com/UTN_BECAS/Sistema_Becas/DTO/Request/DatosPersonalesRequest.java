package com.UTN_BECAS.Sistema_Becas.DTO.Request;

import com.UTN_BECAS.Sistema_Becas.Enum.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class DatosPersonalesRequest {

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser de una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El genero es obligatorio")
    private Genero genero;

    @NotBlank(message = "El celular es obligatorio")
    private String celular;

    @NotBlank(message = "La calle es obligatoria")
    private String domicilioCalle;

    @NotNull(message = "El numero de domicilio es obligatorio")
    private Integer domicilioNumero;

    private String domicilioPisoDepto;

    @NotBlank(message = "El codigo postal es obligatorio")
    private String codigoPostal;

    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @NotBlank(message = "La nacionalidad es obligatoria")
    private String nacionalidad;

    public DatosPersonalesRequest(){

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDomicilioCalle() {
        return domicilioCalle;
    }

    public void setDomicilioCalle(String domicilioCalle) {
        this.domicilioCalle = domicilioCalle;
    }

    public Integer getDomicilioNumero() {
        return domicilioNumero;
    }

    public void setDomicilioNumero(Integer domicilioNumero) {
        this.domicilioNumero = domicilioNumero;
    }

    public String getDomicilioPisoDepto() {
        return domicilioPisoDepto;
    }

    public void setDomicilioPisoDepto(String domicilioPisoDepto) {
        this.domicilioPisoDepto = domicilioPisoDepto;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
