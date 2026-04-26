package com.UTN_BECAS.Sistema_Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Model.MateriasACursar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriasACursarRepository extends JpaRepository<MateriasACursar, Long> {

    List<MateriasACursar> findByPostulacionId(Long postulacionId);
}
