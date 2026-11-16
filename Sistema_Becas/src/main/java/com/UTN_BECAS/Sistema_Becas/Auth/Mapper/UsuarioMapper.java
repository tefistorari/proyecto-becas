package com.UTN_BECAS.Sistema_Becas.Auth.Mapper;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.UsuarioResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario usuario){
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol().getNombre().name());
        response.setTipoUsuario(
            usuario.getTipoUsuario() != null ? usuario.getTipoUsuario().name() : null
        );
        return response;
    }
}
