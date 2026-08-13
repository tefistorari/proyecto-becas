package com.UTN_BECAS.Sistema_Becas.Convocatorias.Mapper;

import com.UTN_BECAS.Sistema_Becas.Becas.Mapper.BecaMapper;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.Convocatoria;

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
        response.setCupoMaximo(convocatoria.getCupoMaximo());
        if (convocatoria.getInforme() != null){
            response.setInforme(InformeConvocatoriaMapper.toResponse(convocatoria.getInforme()));
        }
        return response;
    }
}
