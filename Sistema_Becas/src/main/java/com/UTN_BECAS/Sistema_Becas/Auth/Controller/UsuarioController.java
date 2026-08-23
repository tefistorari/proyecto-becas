package com.UTN_BECAS.Sistema_Becas.Auth.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.UsuarioResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.Service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponse>> buscarAlumnos(
        @RequestParam String texto
    ) {
        return ResponseEntity.ok(usuarioService.buscarAlumnos(texto));
    }
}
