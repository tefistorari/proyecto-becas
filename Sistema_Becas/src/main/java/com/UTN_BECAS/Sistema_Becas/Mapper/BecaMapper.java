package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.BecaResponse;
import com.UTN_BECAS.Sistema_Becas.Model.Beca;

public class BecaMapper {

    public static BecaResponse toResponse(Beca beca){
        BecaResponse response = new BecaResponse();
        response.setId(beca.getId());
        response.setNombre(beca.getNombre());
        response.setDescripcion(beca.getDescripcion());
        response.setTipoBeca(beca.getTipoBeca());
        response.setRequiereIngenieria(beca.isRequiereIngenieria());
        return response;
    }
}
