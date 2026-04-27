package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Model.Postulacion;

public class PostulacionMapper {

    public static PostulacionResponse toResponse(Postulacion postulacion){
        PostulacionResponse response = new PostulacionResponse();
        response.setId(postulacion.getId());
        response.setFechaEnvio(postulacion.getFechaEnvio());
        response.setEstado(postulacion.getEstado());
        response.setConvocatoria(ConvocatoriaMapper.toResponse(postulacion.getConvocatoria()));
        response.setUsuario(UsuarioMapper.toResponse(postulacion.getUsuario()));
        return response;
    }
}
