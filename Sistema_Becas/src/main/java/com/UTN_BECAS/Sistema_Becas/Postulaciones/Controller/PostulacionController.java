package com.UTN_BECAS.Sistema_Becas.Postulaciones.Controller;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionBaseBisUnificadoRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionBinidUnificadoRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Service.PostulacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping("/base-bis")
    public ResponseEntity<PostulacionResponse> postularBaseBis(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PostulacionBaseBisUnificadoRequest request
            ) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(postulacionService.postularBaseBis(usuarioId,request));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping("/binid")
    public ResponseEntity<PostulacionResponse> postularBinid(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PostulacionBinidUnificadoRequest request) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(postulacionService.postularBinid(usuarioId, request));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @GetMapping("/mis-postulaciones")
    public ResponseEntity<List<PostulacionResponse>> listarMisPostulaciones(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(postulacionService.listarPorUsuario(usuarioId));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping("/{postulacionId}/finalizar")
    public ResponseEntity<PostulacionResponse> finalizar(
            @PathVariable Long postulacionId) {
        return ResponseEntity.ok(postulacionService.finalizar(postulacionId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PostulacionResponse>> listarTodas() {
        return ResponseEntity.ok(postulacionService.listarTodas());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estado")
    public ResponseEntity<List<PostulacionResponse>> listarPorEstado(
            @RequestParam EstadoPostulacion estado) {
        return ResponseEntity.ok(postulacionService.listarPorEstado(estado));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/{id}")
    public ResponseEntity<PostulacionResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(postulacionService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<PostulacionResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoPostulacion estado) {
        return ResponseEntity.ok(postulacionService.cambiarEstado(id, estado));
    }
}