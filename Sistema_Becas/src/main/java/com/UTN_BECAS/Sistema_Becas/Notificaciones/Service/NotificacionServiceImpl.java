package com.UTN_BECAS.Sistema_Becas.Notificaciones.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.RecursoNoEncontradoException;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.DTO.NotificacionResponse;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.Mapper.NotificacionMapper;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.Model.Notificacion;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.Repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements INotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<NotificacionResponse> obtenerTodas(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(NotificacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponse> obtenerNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId)
                .stream()
                .map(NotificacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionResponse marcarComoLeida(Long notificacionId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Notificacion no encontrada"));
        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);
        return NotificacionMapper.toResponse(notificacion);
    }

    @Override
    public void crearNotificacion(Long usuarioId, String mensaje) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        Notificacion notificacion = new Notificacion(usuario, mensaje);
        notificacionRepository.save(notificacion);
    }
}