package com.UTN_BECAS.Sistema_Becas.Convocatorias.Model;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Becas.Model.Beca;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "convocatoria")
public class Convocatoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beca_id", nullable = false)
    private Beca beca;

    @Column(nullable = false)
    private Integer anio;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre", nullable = false)
    private LocalDateTime fechaCierre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoConvocatoria estado;

    @Column(nullable = false)
    private Integer cupoMaximo;

    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    private Usuario creadoPor;

    public Convocatoria(){
    }

    public Convocatoria(Beca beca, Integer anio, LocalDateTime fechaApertura, LocalDateTime fechaCierre,
                        EstadoConvocatoria estado, Integer cupoMaximo, Usuario creadoPor) {
        this.beca = beca;
        this.anio = anio;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.estado = estado;
        this.cupoMaximo = cupoMaximo;
        this.creadoPor = creadoPor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Beca getBeca() {
        return beca;
    }

    public void setBeca(Beca beca) {
        this.beca = beca;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoConvocatoria getEstado() {
        return estado;
    }

    public void setEstado(EstadoConvocatoria estado) {
        this.estado = estado;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Convocatoria)) return false;
        Convocatoria convocatoria = (Convocatoria) o;
        return id != null && id.equals(convocatoria.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
