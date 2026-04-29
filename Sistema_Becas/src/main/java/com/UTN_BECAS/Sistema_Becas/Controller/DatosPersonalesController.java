package com.UTN_BECAS.Sistema_Becas.Controller;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.DatosPersonalesResponse;
import com.UTN_BECAS.Sistema_Becas.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.DatosPersonalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/datos-personales")
public class DatosPersonalesController {

    @Autowired
    private DatosPersonalesService datosPersonalesService;

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping
    public ResponseEntity<DatosPersonalesResponse> crear(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DatosPersonalesRequest request) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(datosPersonalesService.crear(usuarioId, request));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @GetMapping
    public ResponseEntity<DatosPersonalesResponse> buscarMisDatos(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(datosPersonalesService.buscarPorUsuarioId(usuarioId));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @PutMapping
    public ResponseEntity<DatosPersonalesResponse> actualizar(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DatosPersonalesRequest request) {
        Long usuarioId = ((Usuario) userDetails).getId();
        return ResponseEntity.ok(datosPersonalesService.actualizar(usuarioId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{usuarioId}")
    public ResponseEntity<DatosPersonalesResponse> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(datosPersonalesService.buscarPorUsuarioId(usuarioId));
    }
}