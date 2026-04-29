package com.UTN_BECAS.Sistema_Becas.Config;

import com.UTN_BECAS.Sistema_Becas.Enum.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Model.Rol;
import com.UTN_BECAS.Sistema_Becas.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Repository.RolRepository;
import com.UTN_BECAS.Sistema_Becas.Repository.UsuarioRepository;
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