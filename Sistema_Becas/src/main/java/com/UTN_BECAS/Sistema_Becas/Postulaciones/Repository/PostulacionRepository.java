package com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    List<Postulacion> findByUsuarioId(Long usuarioId);

    List<Postulacion> findByEstado(EstadoPostulacion estado);

    List<Postulacion> findByConvocatoriaId(Long convocatoriaId);

    @Query("SELECT p.estado, COUNT(p) FROM Postulacion p " +
            "WHERE p.convocatoria.id = :convocatoriaId GROUP BY p.estado")
    List<Object[]> contarPorEstado(@Param("convocatoriaId") Long convocatoriaId);

    Optional<Postulacion> findByUsuarioIdAndConvocatoriaId(Long usuarioId, Long convocatoriaId);

    boolean existsByUsuarioIdAndConvocatoriaId(Long usuarioId, Long convocatoriaId);

    long countByConvocatoriaId(Long convocatoriaId);

    long countByConvocatoriaIdAndEstado(Long convocatoriaId, EstadoPostulacion estado);

    @Query("""
            SELECT p FROM Postulacion p
            JOIN FETCH p.datosPersonalesHistorial d
            WHERE p.convocatoria.id = :convocatoriaId
              AND p.estado IN :estados
            ORDER BY d.apellido, d.nombre
            """)
    List<Postulacion> findParaReporte(@Param("convocatoriaId") Long convocatoriaId,
            @Param("estados") List<EstadoPostulacion> estados);
}
