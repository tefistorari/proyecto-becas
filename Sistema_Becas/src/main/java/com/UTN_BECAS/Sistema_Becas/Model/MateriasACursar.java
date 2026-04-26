package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.NivelMateria;
import com.UTN_BECAS.Sistema_Becas.Enum.RegimenMateria;
import jakarta.persistence.*;

@Entity
@Table(name = "materias_a_cursar")
public class MateriasACursar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postulacion_id", nullable = false)
    private Postulacion postulacion;

    @Column(nullable = false)
    private String nombreMateria;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_materia", nullable = false)
    private NivelMateria nivelMateria;

    @Enumerated(EnumType.STRING)
    @Column(name = "regimen_materia", nullable = false)
    private RegimenMateria regimenMateria;

    @Column(nullable = false)
    private Integer anioMateria;

    public MateriasACursar(){
    }

    public MateriasACursar(Postulacion postulacion, String nombreMateria, NivelMateria nivelMateria, RegimenMateria regimenMateria, Integer anioMateria) {
        this.postulacion = postulacion;
        this.nombreMateria = nombreMateria;
        this.nivelMateria = nivelMateria;
        this.regimenMateria = regimenMateria;
        this.anioMateria = anioMateria;
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

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public NivelMateria getNivelMateria() {
        return nivelMateria;
    }

    public void setNivelMateria(NivelMateria nivelMateria) {
        this.nivelMateria = nivelMateria;
    }

    public RegimenMateria getRegimenMateria() {
        return regimenMateria;
    }

    public void setRegimenMateria(RegimenMateria regimenMateria) {
        this.regimenMateria = regimenMateria;
    }

    public Integer getAnioMateria() {
        return anioMateria;
    }

    public void setAnioMateria(Integer anioMateria) {
        this.anioMateria = anioMateria;
    }
}
