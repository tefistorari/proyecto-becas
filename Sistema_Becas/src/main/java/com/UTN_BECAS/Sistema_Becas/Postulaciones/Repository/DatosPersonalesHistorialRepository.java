package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.DatosPersonalesHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatosPersonalesHistorialRepository extends JpaRepository<DatosPersonalesHistorial, Long> {

    Optional<DatosPersonalesHistorial> findByPostulacionId(Long postulacionId);
}
