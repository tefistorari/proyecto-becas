package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.UsuarioResponse;
import com.UTN_BECAS.Sistema_Becas.Model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario usuario){
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol().getNombre().name());
        return response;
    }
}
