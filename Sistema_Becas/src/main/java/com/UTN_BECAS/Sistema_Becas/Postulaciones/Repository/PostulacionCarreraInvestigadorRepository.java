package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.PostulacionCarreraInvestigador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostulacionCarreraInvestigadorRepository extends JpaRepository<PostulacionCarreraInvestigador, Long> {
    Optional<PostulacionCarreraInvestigador> findByPostulacionId(Long postulacionId);
}
