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

    // Corre cada minuto (en vez de una vez al día) para soportar convocatorias
    // con ventanas de apertura/cierre acotadas a horas dentro del mismo día
    // (ej: abre 13hs, cierra 23hs). Con un cron diario a medianoche, el cambio
    // de estado quedaría desfasado hasta un día entero. El costo de correr
    // cada minuto es despreciable: son dos SELECT simples por estado sobre
    // una tabla chica, con UPDATE solo cuando efectivamente hay algo que cambiar.
    @Scheduled(cron = "0 * * * * *")
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

    // Ídem: corre cada minuto por la misma razón (ventanas de apertura acotadas a horas)
    @Scheduled(cron = "0 * * * * *")
    public void abrirConvocatoriasProgramadas() {
        List<Convocatoria> programadas = convocatoriaRepository
                .findByEstado(EstadoConvocatoria.PROGRAMADA);

        LocalDateTime ahora = LocalDateTime.now();

        for(Convocatoria convocatoria : programadas) {
          if(!convocatoria.getFechaApertura().isAfter(ahora)) {
            convocatoria.setEstado(EstadoConvocatoria.ABIERTA);
            convocatoriaRepository.save(convocatoria);

            notificacionService.crearNotificacion(
                convocatoria.getCreadoPor().getId(), 
                "La convocatoria de " + convocatoria.getBeca().getNombre() + 
                " del año " + convocatoria.getAnio() + " ha abierto.");
          }
        }
    }

}