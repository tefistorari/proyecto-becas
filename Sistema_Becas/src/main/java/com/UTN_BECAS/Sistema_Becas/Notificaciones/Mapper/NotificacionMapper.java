package com.UTN_BECAS.Sistema_Becas.Notificaciones.Mapper;

import com.UTN_BECAS.Sistema_Becas.Notificaciones.DTO.NotificacionResponse;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.Model.Notificacion;

public class NotificacionMapper {

    public static NotificacionResponse toResponse(Notificacion notificacion){
        NotificacionResponse response = new NotificacionResponse();
        response.setId(notificacion.getId());
        response.setMensaje(notificacion.getMensaje());
        response.setLeida(notificacion.isLeida());
        response.setFechaCreacion(notificacion.getFechaCreacion());
        return response;
    }
}
