package com.UTN_BECAS.Sistema_Becas.Notificaciones.Contoller;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.DTO.NotificacionResponse;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.Service.NotificacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionServiceImpl notificacionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> obtenerTodas(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(notificacionService.obtenerTodas(usuarioId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificacionResponse>> obtenerNoLeidas(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(notificacionService.obtenerNoLeidas(usuarioId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/leida")
    public ResponseEntity<NotificacionResponse> marcarComoLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }
}