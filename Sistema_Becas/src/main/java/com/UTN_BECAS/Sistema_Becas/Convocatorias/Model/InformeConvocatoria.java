package com.UTN_BECAS.Sistema_Becas.Convocatorias.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "informe_convocatoria")
public class InformeConvocatoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "convocatoria_id", nullable = false, unique = true)
    private Convocatoria convocatoria;

    @Column(nullable = false)
    private String nombreOriginal;

    @Column(nullable = false)
    private String ruta;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaSubida;

    @PrePersist
    protected void onCreate() {
        this.fechaSubida = LocalDateTime.now();
    }

    public InformeConvocatoria() {
    }

    public InformeConvocatoria(Convocatoria convocatoria, String nombreOriginal, String ruta) {
        this.convocatoria = convocatoria;
        this.nombreOriginal = nombreOriginal;
        this.ruta = ruta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Convocatoria getConvocatoria() { return convocatoria; }
    public void setConvocatoria(Convocatoria convocatoria) { this.convocatoria = convocatoria; }

    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformeConvocatoria)) return false;
        InformeConvocatoria that = (InformeConvocatoria) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}