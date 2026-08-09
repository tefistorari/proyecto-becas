package com.UTN_BECAS.Sistema_Becas.Notificaciones.Repostory;

import com.UTN_BECAS.Sistema_Becas.Notificaciones.Model.Notificacion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository {

    List<Notificacion> findByUsuarioId(Long usuarioId);
    List<Notificacion> findByUsuarioIdAndLeidaFalse(Long usuarioId);

}
