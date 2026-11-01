package com.UTN_BECAS.Sistema_Becas.Becas.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "beca")
public class Beca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_beca", nullable = false)
    private TipoBeca tipoBeca;

    @Column(name = "requiere_ingenieria", nullable = false)
    private boolean requiereIngenieria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Organismo organismo;

    public Beca() {
    }

    public Beca(String nombre, TipoBeca tipoBeca, boolean requiereIngenieria, Organismo organismo) {
        this.nombre = nombre;
        this.tipoBeca = tipoBeca;
        this.requiereIngenieria = requiereIngenieria;
        this.organismo = organismo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoBeca getTipoBeca() {
        return tipoBeca;
    }

    public void setTipoBeca(TipoBeca tipoBeca) {
        this.tipoBeca = tipoBeca;
    }

    public boolean isRequiereIngenieria() {
        return requiereIngenieria;
    }

    public void setRequiereIngenieria(boolean requiereIngenieria) {
        this.requiereIngenieria = requiereIngenieria;
    }

    public Organismo getOrganismo() {
        return organismo;
    }

    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Beca))
            return false;
        Beca beca = (Beca) o;
        return id != null && id.equals(beca.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
