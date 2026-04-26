package com.UTN_BECAS.Sistema_Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    List<Postulacion> findByUsuarioId(Long usuarioId);

    List<Postulacion> findByEstado(EstadoPostulacion estado);

    List<Postulacion> findByConvocatoriaId(Long convocatoriaId);

    Optional<Postulacion> findByUsuarioIdAndConvocatoriaId(Long usuarioId, Long convocatoriaId);

    boolean existsByUsuarioIdAndConvocatoriaId(Long usuarioId, Long convocatoriaId);
}
