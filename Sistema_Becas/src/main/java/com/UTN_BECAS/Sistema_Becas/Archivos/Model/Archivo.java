package com.UTN_BECAS.Sistema_Becas.Archivos.Model;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.Postulacion;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "archivo")
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postulacion_id", nullable = false)
    private Postulacion postulacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_archivo", nullable = false)
    private TipoArchivo tipoArchivo;

    @Column(nullable = false)
    private String nombreOriginal;

    @Column(nullable = false)
    private String ruta;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaSubida;

    @PrePersist
    protected void onCreate(){
        this.fechaSubida = LocalDateTime.now();
    }

    public Archivo(){

    }

    public Archivo(Postulacion postulacion, TipoArchivo tipoArchivo, String nombreOriginal, String ruta) {
        this.postulacion = postulacion;
        this.tipoArchivo = tipoArchivo;
        this.nombreOriginal = nombreOriginal;
        this.ruta = ruta;
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

    public TipoArchivo getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(TipoArchivo tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Archivo)) return false;
        Archivo archivo = (Archivo) o;
        return id != null && id.equals(archivo.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
