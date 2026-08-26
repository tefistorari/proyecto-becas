package com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums;

public enum CategoriaInvestigador {
    D("Docente con mínimo 4 años en proyectos homologados o con tesis de Maestría/Doctorado"),
    E("Docente con mínimo 2 años de antigüedad en proyectos homologados"),
    F("Docente con participación en proyectos de investigación"),
    G("Nivel inicial - Graduado o alumno avanzado con vocación investigadora");

    private final String descripcion;

    CategoriaInvestigador(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
