package com.UTN_BECAS.Sistema_Becas.Controller;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.AuthResponse;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
