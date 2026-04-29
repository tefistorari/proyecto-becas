package com.UTN_BECAS.Sistema_Becas.Controller;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.ConvocatoriaRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.ConvocatoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/convocatorias")
public class ConvocatoriaController {

    @Autowired
    private ConvocatoriaService convocatoriaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ConvocatoriaResponse> crear(@Valid @RequestBody ConvocatoriaRequest request){
        return ResponseEntity.ok(convocatoriaService.crear(request));
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        convocatoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
