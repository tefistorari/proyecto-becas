package com.UTN_BECAS.Sistema_Becas.Postulaciones.Model;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraGrado;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CategoriaInvestigador;
import jakarta.persistence.*;

@Entity
@Table(name = "postulacion_carrera_investigador")
public class PostulacionCarreraInvestigador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_actual")
    private CategoriaInvestigador categoriaActual;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_solicitada", nullable = false)
    private CategoriaInvestigador categoriaSolicitada;

    @Column(nullable = false)
    private String materia;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrera_grado", nullable = false)
    private CarreraGrado carreraGrado;

    public PostulacionCarreraInvestigador(){}

    public PostulacionCarreraInvestigador(
            Postulacion postulacion, CategoriaInvestigador categoriaActual,
            CategoriaInvestigador categoriaSolicitada, String materia,
            CarreraGrado carreraGrado
    ){
        this.postulacion = postulacion;
        this.categoriaActual = categoriaActual;
        this.categoriaSolicitada = categoriaSolicitada;
        this.materia = materia;
        this.carreraGrado = carreraGrado;
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

    public CategoriaInvestigador getCategoriaActual() {
        return categoriaActual;
    }

    public void setCategoriaActual(CategoriaInvestigador categoriaActual) {
        this.categoriaActual = categoriaActual;
    }

    public CategoriaInvestigador getCategoriaSolicitada() {
        return categoriaSolicitada;
    }

    public void setCategoriaSolicitada(CategoriaInvestigador categoriaSolicitada) {
        this.categoriaSolicitada = categoriaSolicitada;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public CarreraGrado getCarreraGrado() {
        return carreraGrado;
    }

    public void setCarreraGrado(CarreraGrado carreraGrado) {
        this.carreraGrado = carreraGrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostulacionCarreraInvestigador)) return false;
        PostulacionCarreraInvestigador that = (PostulacionCarreraInvestigador) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
