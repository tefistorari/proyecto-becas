package com.UTN_BECAS.Sistema_Becas.Auth.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.UsuarioResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.Mapper.UsuarioMapper;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private static final int LIMITE_BUSQUEDA = 10;
    private static final int MIN_CARACTERES = 3;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioResponse> buscarAlumnos(String texto) {
        String t = texto == null ? "" : texto.trim();
        if (t.length() < MIN_CARACTERES) {
            return List.of();
        }

        List<Usuario> usuarios = usuarioRepository.buscarAlumnos(
            t, NombreRol.ALUMNO, PageRequest.of(0, LIMITE_BUSQUEDA));

        return usuarios.stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

}
