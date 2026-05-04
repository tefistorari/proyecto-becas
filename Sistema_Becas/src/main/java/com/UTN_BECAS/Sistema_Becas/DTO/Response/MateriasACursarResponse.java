package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.NivelMateria;
import com.UTN_BECAS.Sistema_Becas.Enum.RegimenMateria;

public class MateriasACursarResponse {

    private Long id;
    private String nombreMateria;
    private NivelMateria nivelMateria;
    private RegimenMateria regimenMateria;
    private Integer anioMateria;

    public MateriasACursarResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public NivelMateria getNivelMateria() { return nivelMateria; }
    public void setNivelMateria(NivelMateria nivelMateria) { this.nivelMateria = nivelMateria; }

    public RegimenMateria getRegimenMateria() { return regimenMateria; }
    public void setRegimenMateria(RegimenMateria regimenMateria) { this.regimenMateria = regimenMateria; }

    public Integer getAnioMateria() { return anioMateria; }
    public void setAnioMateria(Integer anioMateria) { this.anioMateria = anioMateria; }
}
