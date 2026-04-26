package com.UTN_BECAS.Sistema_Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Model.DatosPersonalesHistorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatosPersonalesHistorialRepository extends JpaRepository<DatosPersonalesHistorial, Long> {

    Optional<DatosPersonalesHistorial> findByPostulacionId(Long postulacionId);
}
