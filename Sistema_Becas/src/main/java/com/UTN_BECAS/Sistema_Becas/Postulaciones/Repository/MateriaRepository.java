package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CarreraBaseBis;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.Materia;

public interface MateriaRepository extends JpaRepository <Materia, Long>{
    List<Materia> findByCarreraOrderByNombreAsc(CarreraBaseBis carrera);
}
