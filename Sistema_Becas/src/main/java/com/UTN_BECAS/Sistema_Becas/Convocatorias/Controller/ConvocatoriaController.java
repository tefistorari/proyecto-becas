package com.UTN_BECAS.Sistema_Becas.Convocatorias.Controller;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaRequest;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.EstadisticasConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.InformeConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Service.ConvocatoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaController {

    @Autowired
    private ConvocatoriaService convocatoriaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ConvocatoriaResponse> crear(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ConvocatoriaRequest request) {
        Long adminId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(convocatoriaService.crear(request, adminId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ConvocatoriaResponse>> listarTodas(){
        return ResponseEntity.ok(convocatoriaService.listarTodas());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/abiertas")
    public ResponseEntity<List<ConvocatoriaResponse>> listarAbiertas(){
        return ResponseEntity.ok(convocatoriaService.listarPorEstado(EstadoConvocatoria.ABIERTA));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/{id}")
    public ResponseEntity<ConvocatoriaResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(convocatoriaService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ConvocatoriaResponse> actualizar(@PathVariable Long id,
                                                           @Valid @RequestBody ConvocatoriaRequest request) {
        return ResponseEntity.ok(convocatoriaService.actualizar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<ConvocatoriaResponse> cambiarEstado(@PathVariable Long id,
                                                              @RequestParam EstadoConvocatoria estado) {
        return ResponseEntity.ok(convocatoriaService.cambiarEstado(id, estado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<EstadisticasConvocatoriaResponse> obtenerEstadisticas(
            @PathVariable Long id) {
        return ResponseEntity.ok(convocatoriaService.obtenerEstadisticas(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/informe")
    public ResponseEntity<InformeConvocatoriaResponse> subirInforme(
            @PathVariable Long id,
            @RequestParam MultipartFile file) {
        return ResponseEntity.ok(convocatoriaService.subirInforme(id, file));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/informe")
    public ResponseEntity<InformeConvocatoriaResponse> obtenerInforme(
            @PathVariable Long id) {
        return ResponseEntity.ok(convocatoriaService.obtenerInforme(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        convocatoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
