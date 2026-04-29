package com.UTN_BECAS.Sistema_Becas.Controller;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.BecaRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.BecaResponse;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.BecaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/becas")
public class BecaController {

    @Autowired
    private BecaService becaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BecaResponse> crear(@Valid @RequestBody BecaRequest request){
        return ResponseEntity.ok(becaService.crear(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping
    public ResponseEntity<List<BecaResponse>> listarTodas(){
        return ResponseEntity.ok(becaService.listarTodas());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ALUMNO')")
    @GetMapping("/{id}")
    public ResponseEntity<BecaResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(becaService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public  ResponseEntity<BecaResponse> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody BecaRequest request){
        return ResponseEntity.ok(becaService.actualizar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        becaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
