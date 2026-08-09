package com.UTN_BECAS.Sistema_Becas.Notificaciones.DTO;

import java.time.LocalDateTime;

public class NotificacionResponse {

    private Long id;
    private String mensaje;
    private boolean leida;
    private LocalDateTime fechaCreacion;

    public NotificacionResponse() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}