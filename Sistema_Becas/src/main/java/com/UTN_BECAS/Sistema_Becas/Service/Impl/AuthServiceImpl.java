package com.UTN_BECAS.Sistema_Becas.Service.Impl;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.AuthResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Model.Rol;
import com.UTN_BECAS.Sistema_Becas.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Repository.RolRepository;
import com.UTN_BECAS.Sistema_Becas.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Security.JwtUtil;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.AuthService;
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
            throw new RuntimeException("El email ya esta registrado");
        }

        Rol rol = rolRepository.findByNombre(NombreRol.ALUMNO)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rol);

        usuarioRepository.save(usuario);

        AuthResponse response = new AuthResponse();
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(rol.getNombre().name());
        response.setToken(null);

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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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
}
