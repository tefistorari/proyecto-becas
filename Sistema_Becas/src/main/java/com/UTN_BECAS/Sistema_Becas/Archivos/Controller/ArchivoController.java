package com.UTN_BECAS.Sistema_Becas.Archivos.Controller;

import com.UTN_BECAS.Sistema_Becas.Archivos.DTO.ArchivoDescarga;
import com.UTN_BECAS.Sistema_Becas.Archivos.DTO.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Archivos.Model.TipoArchivo;
import com.UTN_BECAS.Sistema_Becas.Archivos.Service.ArchivoService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    private boolean esAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @PostMapping("/{postulacionId}")
    public ResponseEntity<ArchivoResponse> subir(
            @PathVariable Long postulacionId,
            @RequestParam TipoArchivo tipoArchivo,
            @RequestParam MultipartFile file,
            Authentication authentication) {
        return ResponseEntity.ok(
                archivoService.subir(postulacionId, tipoArchivo, file, authentication.getName()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/{postulacionId}")
    public ResponseEntity<List<ArchivoResponse>> listarPorPostulacion(
            @PathVariable Long postulacionId,
            Authentication authentication) {
        return ResponseEntity.ok(
                archivoService.listarPorPostulacion(postulacionId, authentication.getName(), esAdmin(authentication)));
    }

    @PreAuthorize("hasRole('ALUMNO')")
    @DeleteMapping("/{archivoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long archivoId,
            Authentication authentication) {
        archivoService.eliminar(archivoId, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/{archivoId}/descargar")
    public ResponseEntity<Resource> descargar(@PathVariable Long archivoId,
            Authentication authentication) {
        boolean esAdmin = esAdmin(authentication);

        ArchivoDescarga d = archivoService.descargar(archivoId, authentication.getName(), esAdmin);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(d.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename(d.nombreOriginal(), StandardCharsets.UTF_8)
                                .build().toString())
                .body(d.recurso());
    }
}