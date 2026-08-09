package com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO;

import com.UTN_BECAS.Sistema_Becas.Estudiantes.Model.Genero;
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

    @NotNull(message = "Debe indicar si el domicilio familiar es distinto")
    private Boolean domicilioFamiliarDistinto = false;

    private String domicilioFamiliarCalle;
    private Integer domicilioFamiliarNumero;
    private String domicilioFamiliarPisoDepto;
    private String domicilioFamiliarCodigoPostal;
    private String domicilioFamiliarLocalidad;
    private String domicilioFamiliarProvincia;

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

    public Boolean getDomicilioFamiliarDistinto() {
        return domicilioFamiliarDistinto;
    }

    public void setDomicilioFamiliarDistinto(Boolean domicilioFamiliarDistinto) {
        this.domicilioFamiliarDistinto = domicilioFamiliarDistinto;
    }

    public String getDomicilioFamiliarCalle() {
        return domicilioFamiliarCalle;
    }

    public void setDomicilioFamiliarCalle(String domicilioFamiliarCalle) {
        this.domicilioFamiliarCalle = domicilioFamiliarCalle;
    }

    public Integer getDomicilioFamiliarNumero() {
        return domicilioFamiliarNumero;
    }

    public void setDomicilioFamiliarNumero(Integer domicilioFamiliarNumero) {
        this.domicilioFamiliarNumero = domicilioFamiliarNumero;
    }

    public String getDomicilioFamiliarPisoDepto() {
        return domicilioFamiliarPisoDepto;
    }

    public void setDomicilioFamiliarPisoDepto(String domicilioFamiliarPisoDepto) {
        this.domicilioFamiliarPisoDepto = domicilioFamiliarPisoDepto;
    }

    public String getDomicilioFamiliarCodigoPostal() {
        return domicilioFamiliarCodigoPostal;
    }

    public void setDomicilioFamiliarCodigoPostal(String domicilioFamiliarCodigoPostal) {
        this.domicilioFamiliarCodigoPostal = domicilioFamiliarCodigoPostal;
    }

    public String getDomicilioFamiliarLocalidad() {
        return domicilioFamiliarLocalidad;
    }

    public void setDomicilioFamiliarLocalidad(String domicilioFamiliarLocalidad) {
        this.domicilioFamiliarLocalidad = domicilioFamiliarLocalidad;
    }

    public String getDomicilioFamiliarProvincia() {
        return domicilioFamiliarProvincia;
    }

    public void setDomicilioFamiliarProvincia(String domicilioFamiliarProvincia) {
        this.domicilioFamiliarProvincia = domicilioFamiliarProvincia;
    }
}
