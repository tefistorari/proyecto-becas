package com.UTN_BECAS.Sistema_Becas.Convocatorias.Service;

import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.Convocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Repository.ConvocatoriaRepository;
import com.UTN_BECAS.Sistema_Becas.Notificaciones.Service.NotificacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ConvocatoriaScheduler {

    @Autowired
    private ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private NotificacionServiceImpl notificacionService;

    @Scheduled(cron = "0 0 0 * * *")
    public void cerrarConvocatoriasVencidas() {
        List<Convocatoria> convocatorias = convocatoriaRepository
                .findByEstado(EstadoConvocatoria.ABIERTA);

        LocalDateTime ahora = LocalDateTime.now();

        for (Convocatoria convocatoria : convocatorias) {
            if (convocatoria.getFechaCierre().isBefore(ahora)) {
                convocatoria.setEstado(EstadoConvocatoria.CERRADA);
                convocatoriaRepository.save(convocatoria);

                // Notificar al admin que creó la convocatoria
                notificacionService.crearNotificacion(
                        convocatoria.getCreadoPor().getId(),
                        "La convocatoria de " + convocatoria.getBeca().getNombre() +
                                " del año " + convocatoria.getAnio() + " ha finalizado."
                );
            }
        }
    }
}