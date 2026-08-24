package com.UTN_BECAS.Sistema_Becas.Auth.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.UsuarioResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.Mapper.UsuarioMapper;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioResponse> buscarAlumnos(String texto) {
        List<Usuario> usuarios = usuarioRepository.buscarAlumnos(texto, NombreRol.ALUMNO);

        return usuarios.stream()
                .map(UsuarioMapper::toResponse).toList();
    }

}
