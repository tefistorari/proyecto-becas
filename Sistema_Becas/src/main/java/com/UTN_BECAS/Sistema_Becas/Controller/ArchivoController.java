package com.UTN_BECAS.Sistema_Becas.Controller;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.TipoArchivo;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping("/{postulacionId}")
    public ResponseEntity<ArchivoResponse> subir(
            @PathVariable Long postulacionId,
            @RequestParam TipoArchivo tipoArchivo,
            @RequestParam MultipartFile file) {
        return ResponseEntity.ok(archivoService.subir(postulacionId, tipoArchivo, file));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/{postulacionId}")
    public ResponseEntity<List<ArchivoResponse>> listarPorPostulacion(
            @PathVariable Long postulacionId) {
        return ResponseEntity.ok(archivoService.listarPorPostulacion(postulacionId));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @DeleteMapping("/{archivoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long archivoId) {
        archivoService.eliminar(archivoId);
        return ResponseEntity.noContent().build();
    }
}