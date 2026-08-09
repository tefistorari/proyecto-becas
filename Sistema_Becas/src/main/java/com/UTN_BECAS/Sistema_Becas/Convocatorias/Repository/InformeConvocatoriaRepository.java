package com.UTN_BECAS.Sistema_Becas.Convocatorias.Repository;

import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.InformeConvocatoria;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformeConvocatoriaRepository {

    Optional<InformeConvocatoria> findByConvocatoriaId(Long convocatoriaId);
}
