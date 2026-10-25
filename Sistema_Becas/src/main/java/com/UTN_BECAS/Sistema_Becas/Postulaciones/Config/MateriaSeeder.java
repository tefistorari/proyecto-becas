package com.UTN_BECAS.Sistema_Becas.Postulaciones.Config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraBaseBis;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.Materia;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository.MateriaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MateriaSeeder implements CommandLineRunner {
    private final MateriaRepository repo;
    private final ObjectMapper mapper;

    public MateriaSeeder(MateriaRepository repo, ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    record MateriaSeed(String nombre, Integer anio) {
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() > 0)
            return;

        try (var in = new ClassPathResource("materias.json").getInputStream()) {
            Map<CarreraBaseBis, List<String>> data = mapper.readValue(in, new TypeReference<>() {
            });

            data.forEach((carrera, materias) -> materias.forEach(nombre -> repo.save(new Materia(nombre, carrera))));
        }
    }
}
