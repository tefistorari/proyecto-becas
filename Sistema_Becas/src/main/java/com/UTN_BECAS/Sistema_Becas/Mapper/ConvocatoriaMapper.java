package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Model.Convocatoria;

public class ConvocatoriaMapper {

    public static ConvocatoriaResponse toResponse(Convocatoria convocatoria){
        ConvocatoriaResponse response = new ConvocatoriaResponse();
        response.setId(convocatoria.getId());
        response.setAnio(convocatoria.getAnio());
        response.setFechaApertura(convocatoria.getFechaApertura());
        response.setFechaCierre(convocatoria.getFechaCierre());
        response.setDescripcion(convocatoria.getDescripcion());
        response.setEstado(convocatoria.getEstado());
        response.setBeca(BecaMapper.toResponse(convocatoria.getBeca()));
        return response;
    }
}
