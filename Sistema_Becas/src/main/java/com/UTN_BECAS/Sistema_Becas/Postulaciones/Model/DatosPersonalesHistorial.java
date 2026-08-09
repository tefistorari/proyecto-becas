package com.UTN_BECAS.Sistema_Becas.Postulaciones.Model;

import com.UTN_BECAS.Sistema_Becas.Estudiantes.Model.Genero;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "datos_personales_historial")
public class DatosPersonalesHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String dni;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private String domicilioCalle;

    @Column(nullable = false)
    private Integer domicilioNumero;

    private String domicilioPisoDepto;

    @Column(nullable = false)
    private String codigoPostal;

    @Column(nullable = false)
    private String localidad;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String nacionalidad;

    @Column(nullable = false)
    private boolean domicilioFamiliarDistinto = false;

    @Column(nullable = true)
    private String domicilioFamiliarCalle;

    @Column(nullable = true)
    private Integer domicilioFamiliarNumero;

    private String domicilioFamiliarPisoDepto;

    @Column(nullable = true)
    private String domicilioFamiliarCodigoPostal;

    @Column(nullable = true)
    private String domicilioFamiliarLocalidad;

    @Column(nullable = true)
    private String domicilioFamiliarProvincia;

    public DatosPersonalesHistorial() {
    }

    public DatosPersonalesHistorial(
            Postulacion postulacion, String nombre, String apellido, String dni,
            LocalDate fechaNacimiento, Genero genero, String celular, String domicilioCalle,
            Integer domicilioNumero, String codigoPostal, String localidad, String provincia,
            String nacionalidad, boolean domicilioFamiliarDistinto, String domicilioFamiliarCalle,
            Integer domicilioFamiliarNumero, String domicilioFamiliarCodigoPostal, String domicilioFamiliarLocalidad,
            String domicilioFamiliarProvincia
            ) {
        this.postulacion = postulacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.celular = celular;
        this.domicilioCalle = domicilioCalle;
        this.domicilioNumero = domicilioNumero;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.nacionalidad = nacionalidad;
        this.domicilioFamiliarDistinto = domicilioFamiliarDistinto;
        this.domicilioFamiliarCalle = domicilioFamiliarCalle;
        this.domicilioFamiliarNumero = domicilioFamiliarNumero;
        this.domicilioFamiliarCodigoPostal = domicilioFamiliarCodigoPostal;
        this.domicilioFamiliarLocalidad = domicilioFamiliarLocalidad;
        this.domicilioFamiliarProvincia = domicilioFamiliarProvincia;
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

    public boolean isDomicilioFamiliarDistinto() {
        return domicilioFamiliarDistinto;
    }

    public void setDomicilioFamiliarDistinto(boolean domicilioFamiliarDistinto) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatosPersonalesHistorial)) return false;
        DatosPersonalesHistorial that = (DatosPersonalesHistorial) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
