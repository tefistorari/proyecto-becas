package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.PostulacionBecaBinid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostulacionBecaBinidRepository extends JpaRepository<PostulacionBecaBinid, Long> {

    Optional<PostulacionBecaBinid> findByPostulacionId(Long postulacionId);
}
