package com.UTN_BECAS.Sistema_Becas.Estudiantes.Mapper;

import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesResponse;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.Model.DatosPersonales;

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
        response.setDomicilioFamiliarDistinto(datosPersonales.isDomicilioFamiliarDistinto());
        response.setDomicilioFamiliarCalle(datosPersonales.getDomicilioFamiliarCalle());
        response.setDomicilioFamiliarNumero(datosPersonales.getDomicilioFamiliarNumero());
        response.setDomicilioFamiliarPisoDepto(datosPersonales.getDomicilioFamiliarPisoDepto());
        response.setDomicilioFamiliarCodigoPostal(datosPersonales.getDomicilioFamiliarCodigoPostal());
        response.setDomicilioFamiliarLocalidad(datosPersonales.getDomicilioFamiliarLocalidad());
        response.setDomicilioFamiliarProvincia(datosPersonales.getDomicilioFamiliarProvincia());
        return response;
    }
}