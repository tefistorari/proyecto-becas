package com.UTN_BECAS.Sistema_Becas.Auth.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.AuthResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.ChangePasswordRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Rol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.RolRepository;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Auth.Security.JwtUtil;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ConflictoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.RecursoNoEncontradoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ReglaDeNegocioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request){
        if (usuarioRepository.existsByEmail(request.getEmail())){
            throw new ConflictoException("El email ya esta registrado");
        }

        Rol rol = rolRepository.findByNombre(NombreRol.ALUMNO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);

        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail());

        AuthResponse response = new AuthResponse();
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(rol.getNombre().name());
        response.setToken(token);

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request){
        //Verifica credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //Busca el usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        //Genera el token JWT
        String token = jwtUtil.generateToken(usuario.getEmail());

        //Arma la respuesta
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol().getNombre().name());

        return response;
    }

    @Override
    public void changePassword(Long usuarioId, ChangePasswordRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if(!passwordEncoder.matches(request.getPasswordActual(), usuario.getPasswordHash())) {
            throw new ReglaDeNegocioException("La contraseña actual es incorrecta");
        }          
        
        if(passwordEncoder.matches(request.getPasswordNueva(), usuario.getPasswordHash())) {
            throw new ReglaDeNegocioException("La nueva contraseña debe ser distinta a la actual");
        }

        usuario.setPasswordHash(passwordEncoder.encode(request.getPasswordNueva()));
        usuarioRepository.save(usuario);
    }

}
