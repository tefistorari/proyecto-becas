package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.PostulacionBecaBaseBis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostulacionBecaBaseBisRepository extends JpaRepository<PostulacionBecaBaseBis, Long> {

    Optional<PostulacionBecaBaseBis> findByPostulacionId(Long postulacionId);
}
