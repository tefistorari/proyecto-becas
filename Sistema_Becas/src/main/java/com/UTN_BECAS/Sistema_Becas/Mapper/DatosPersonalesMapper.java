package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.DatosPersonalesResponse;
import com.UTN_BECAS.Sistema_Becas.Model.DatosPersonales;

public class DatosPersonalesMapper {

    public static DatosPersonalesResponse toResponse(DatosPersonales datosPersonales){
        DatosPersonalesResponse response = new DatosPersonalesResponse();
        response.setId(datosPersonales.getId());
        response.setDni(datosPersonales.getDni());
        response.setFechaNacimiento(datosPersonales.getFechaNacimiento());
        response.setGenero(datosPersonales.getGenero());
        response.setCelular(datosPersonales.getCelular());
        response.setDomicilioCalle(datosPersonales.getDomicilioCalle());
        response.setDomicilioNumero(datosPersonales.getDomicilioNumero());
        response.setDomicilioPisoDepto(datosPersonales.getDomicilioPisoDepto());
        response.setCodigoPostal(datosPersonales.getCodigoPostal());
        response.setLocalidad(datosPersonales.getLocalidad());
        response.setProvincia(datosPersonales.getProvincia());
        response.setNacionalidad(datosPersonales.getNacionalidad());
        return response;
    }
}
