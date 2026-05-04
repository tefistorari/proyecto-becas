package com.UTN_BECAS.Sistema_Becas.DTO.Response;

import com.UTN_BECAS.Sistema_Becas.Enum.MesMesa;
import com.UTN_BECAS.Sistema_Becas.Enum.NivelMateria;

public class MateriasARendirResponse {

    private Long id;
    private String nombreMateria;
    private NivelMateria nivelMateria;
    private MesMesa mesMesa;
    private Integer anioMesa;

    public MateriasARendirResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public NivelMateria getNivelMateria() { return nivelMateria; }
    public void setNivelMateria(NivelMateria nivelMateria) { this.nivelMateria = nivelMateria; }

    public MesMesa getMesMesa() { return mesMesa; }
    public void setMesMesa(MesMesa mesMesa) { this.mesMesa = mesMesa; }

    public Integer getAnioMesa() { return anioMesa; }
    public void setAnioMesa(Integer anioMesa) { this.anioMesa = anioMesa; }
}
