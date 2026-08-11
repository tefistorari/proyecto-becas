package com.UTN_BECAS.Sistema_Becas.Auth.Config;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Rol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.RolRepository;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Becas.Model.Beca;
import com.UTN_BECAS.Sistema_Becas.Becas.Model.TipoBeca;
import com.UTN_BECAS.Sistema_Becas.Becas.Repository.BecaRepository;

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

    @Autowired
    private BecaRepository becaRepository;

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

        // Crear becas si no existen
        crearBecas();
    }

    private void crearBecas() {

        if(becaRepository.findByTipoBeca(TipoBeca.BASE).isEmpty()) {
            Beca base = new Beca(
                "Beca de Ayuda Social Economica",
                TipoBeca.BASE,
                false
            );

            base.setDescripcion("Beca de Ayuda Social y Económica destinada a estudiantes que necesitan apoyo "
            + "para afrontar necesidades básicas como transporte, apuntes, residencia y todas "
            + "aquellas que puedan limitar sus posibilidades de estudio."    
            );

            becaRepository.save(base);
        }

        if (becaRepository.findByTipoBeca(TipoBeca.BIS).isEmpty()) {
            Beca bis = new Beca(
                "Beca de Investigación/Servicio",
                TipoBeca.BIS,
                false
            );

            bis.setDescripcion("Beca de Investigación y Servicio destinada a estudiantes para participar "
                + "en proyectos de investigacion y desarrollar tareas técnicas no administrativas "
                + "que contribuyan a cubrir necesidades de la Facultad Regional."
            );

            becaRepository.save(bis);
        }

        if (becaRepository.findByTipoBeca(TipoBeca.BINID).isEmpty()) {
            Beca binid = new Beca(
                "Beca Binid",
                TipoBeca.BINID,
                true
            );

            binid.setDescripcion("Beca de Iniciación en Investigación y Desarrollo destinada a estudiantes avanzados "
                + "y recientes graduados para incorporarse a proyectos cientifico-tecnologicos y "
                + "adquirir experiencia en investigación y desarrollo."
            );

            becaRepository.save(binid);
        }
    }
}