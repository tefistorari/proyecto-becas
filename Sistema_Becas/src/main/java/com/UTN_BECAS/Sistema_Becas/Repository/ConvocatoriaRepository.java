package com.UTN_BECAS.Sistema_Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Enum.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Model.Convocatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {

    List<Convocatoria> findByEstado(EstadoConvocatoria estado);

    List<Convocatoria> findByBecaId(Long becaId);

    List<Convocatoria> findByAnio(Integer anio);
}
