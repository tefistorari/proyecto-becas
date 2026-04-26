package com.UTN_BECAS.Sistema_Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Model.MateriasARendir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriasARendirRepository extends JpaRepository<MateriasARendir, Long> {

    List<MateriasARendir> findByPostulacionId(Long postulacionId);
}
