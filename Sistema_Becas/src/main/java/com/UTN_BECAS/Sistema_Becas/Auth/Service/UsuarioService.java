package com.UTN_BECAS.Sistema_Becas.Auth.Service;

import java.util.List;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.UsuarioResponse;

public interface UsuarioService {
    List<UsuarioResponse> buscarAlumnos(String texto);
}
