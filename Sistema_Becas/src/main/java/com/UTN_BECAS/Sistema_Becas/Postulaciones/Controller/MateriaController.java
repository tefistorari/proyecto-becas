package com.UTN_BECAS.Sistema_Becas.Postulaciones.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.MateriaResponse;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraBaseBis;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository.MateriaRepository;

@RestController 
@RequestMapping("api/materias")
public class MateriaController {
    private final MateriaRepository repo;

    public MateriaController(MateriaRepository repo) {
        this.repo = repo;
    }

     @GetMapping
    public List<MateriaResponse> listar(@RequestParam CarreraBaseBis carrera) {
        return repo.findByCarreraOrderByNombreAsc(carrera).stream()
        .map(m -> new MateriaResponse(m.getId(), m.getNombre()))
        .toList();
    }
}

