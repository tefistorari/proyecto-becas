package com.UTN_BECAS.Sistema_Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
}
