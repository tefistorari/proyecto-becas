package com.UTN_BECAS.Sistema_Becas.Notificaciones.Service;

import com.UTN_BECAS.Sistema_Becas.Notificaciones.DTO.NotificacionResponse;

import java.util.List;

public interface INotificacionService {

    List<NotificacionResponse> obtenerTodas(Long usuarioId);
    List<NotificacionResponse> obtenerNoLeidas(Long usuarioId);
    NotificacionResponse marcarComoLeida(Long notificacionId);
    void crearNotificacion(Long usuarioId, String mensaje);
}
