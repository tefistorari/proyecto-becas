package com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO;

import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraGrado;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CategoriaInvestigador;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostulacionCarreraInvestigadorUnificadoRequest {

    @NotNull(message = "La convocatoria es obligatoria")
    private Long convocatoriaId;

    @NotNull(message = "Los datos personales son obligatorios")
    @Valid
    private DatosPersonalesRequest datosPersonales;

    private CategoriaInvestigador categoriaActual;

    @NotNull(message = "La categoria solicitada es obligatoria")
    private CategoriaInvestigador categoriaSolicitada;

    @NotBlank(message = "La materia es obligatoria")
    private String materia;

    @NotNull(message = "La carrera es obligatoria")
    private CarreraGrado carreraGrado;

    public PostulacionCarreraInvestigadorUnificadoRequest (){
    }

    public Long getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(Long convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }

    public DatosPersonalesRequest getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(DatosPersonalesRequest datosPersonales) {
        this.datosPersonales = datosPersonales;
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
}
