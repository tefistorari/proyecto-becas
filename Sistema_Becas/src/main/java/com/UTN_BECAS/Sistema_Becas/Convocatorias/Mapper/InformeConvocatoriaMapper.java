package com.UTN_BECAS.Sistema_Becas.Convocatorias.Mapper;

import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.InformeConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.InformeConvocatoria;

public class InformeConvocatoriaMapper {

    public static InformeConvocatoriaResponse toResponse(InformeConvocatoria informe){
        InformeConvocatoriaResponse response = new InformeConvocatoriaResponse();
        response.setId(informe.getId());
        response.setFechaSubida(informe.getFechaSubida());
        response.setNombreOriginal(informe.getNombreOriginal());
        return response;
    }
}
