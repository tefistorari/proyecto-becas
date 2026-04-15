package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.MesMesa;
import com.UTN_BECAS.Sistema_Becas.Enum.NivelMateria;
import jakarta.persistence.*;

@Entity
@Table(name = "materias_a_rendir")
public class MateriasARendir {

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
    @Column(name = "mes_mesa", nullable = false)
    private MesMesa mesMesa;

    @Column(nullable = false)
    private Integer anioMesa;

    public MateriasARendir(){
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

    public MesMesa getMesMesa() {
        return mesMesa;
    }

    public void setMesMesa(MesMesa mesMesa) {
        this.mesMesa = mesMesa;
    }

    public Integer getAnioMesa() {
        return anioMesa;
    }

    public void setAnioMesa(Integer anioMesa) {
        this.anioMesa = anioMesa;
    }
}
