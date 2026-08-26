package com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CategoriaInvestigador;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraGrado;

public class PostulacionCarreraInvestigadorResponse {

    private CategoriaInvestigador categoriaActual;
    private CategoriaInvestigador categoriaSolicitada;
    private String materia;
    private CarreraGrado carreraGrado;

    public PostulacionCarreraInvestigadorResponse() {
    }

    public CategoriaInvestigador getCategoriaActual() { return categoriaActual; }
    public void setCategoriaActual(CategoriaInvestigador categoriaActual) { this.categoriaActual = categoriaActual; }

    public CategoriaInvestigador getCategoriaSolicitada() { return categoriaSolicitada; }
    public void setCategoriaSolicitada(CategoriaInvestigador categoriaSolicitada) { this.categoriaSolicitada = categoriaSolicitada; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public CarreraGrado getCarreraGrado() { return carreraGrado; }
    public void setCarreraGrado(CarreraGrado carreraGrado) { this.carreraGrado = carreraGrado; }
}