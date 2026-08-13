package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.HistorialBecaBinid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialBecaBinidRepository extends JpaRepository<HistorialBecaBinid, Long> {

    long countByUsuarioId(Long usuarioId);
}
