package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import org.springframework.stereotype.Repository;

@Repository
public interface HistorialBecaBinidRepository {

    long countByUsuarioId(Long usuarioId);
}
