package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.TipoBeca;
import jakarta.persistence.*;

@Entity
@Table(name = "beca")
public class Beca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_beca", nullable = false)
    private TipoBeca tipoBeca;

    @Column(name = "requiere_ingenieria")
    private Boolean requiereIngenieria;

    public Beca(){
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

    public Boolean getRequiereIngenieria() {
        return requiereIngenieria;
    }

    public void setRequiereIngenieria(Boolean requiereIngenieria) {
        this.requiereIngenieria = requiereIngenieria;
    }


}
