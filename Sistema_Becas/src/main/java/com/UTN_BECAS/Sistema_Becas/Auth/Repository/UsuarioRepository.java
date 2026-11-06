package com.UTN_BECAS.Sistema_Becas.Auth.Repository;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.NombreRol;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u
        FROM Usuario u
        WHERE u.rol.nombre = :rol
        AND (
            LOWER(CONCAT(u.nombre, ' ', u.apellido)) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(CONCAT(u.apellido, ' ', u.nombre)) LIKE LOWER(CONCAT('%', :texto, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :texto, '%'))
        )
        ORDER BY u.apellido, u.nombre
    """)
    List<Usuario> buscarAlumnos(
            @Param("texto") String texto,
            @Param("rol") NombreRol rol,
            Pageable pageable
    );
}
