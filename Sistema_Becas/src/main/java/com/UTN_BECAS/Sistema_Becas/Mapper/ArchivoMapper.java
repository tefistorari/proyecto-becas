package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Model.Archivo;

public class ArchivoMapper {

    public static ArchivoResponse toResponse(Archivo archivo){
        ArchivoResponse response = new ArchivoResponse();
        response.setId(archivo.getId());
        response.setNombreOriginal(archivo.getNombreOriginal());
        response.setTipoArchivo(archivo.getTipoArchivo());
        response.setFechaSubida(archivo.getFechaSubida());
        return response;
    }
}
