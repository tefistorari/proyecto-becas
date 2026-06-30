package com.UTN_BECAS.Sistema_Becas.Auth.Config;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Rol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.RolRepository;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Crear roles si no existen
        if (rolRepository.findByNombre(NombreRol.ADMIN).isEmpty()) {
            Rol admin = new Rol();
            admin.setNombre(NombreRol.ADMIN);
            rolRepository.save(admin);
        }

        if (rolRepository.findByNombre(NombreRol.ALUMNO).isEmpty()) {
            Rol alumno = new Rol();
            alumno.setNombre(NombreRol.ALUMNO);
            rolRepository.save(alumno);
        }

        // Crear usuario admin por defecto si no existe
        if (!usuarioRepository.existsByEmail("admin@utn.edu.ar")) {
            Rol rolAdmin = rolRepository.findByNombre(NombreRol.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Rol admin no encontrado"));

            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellido("Sistema");
            admin.setEmail("admin@utn.edu.ar");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRol(rolAdmin);

            usuarioRepository.save(admin);
        }
    }
}