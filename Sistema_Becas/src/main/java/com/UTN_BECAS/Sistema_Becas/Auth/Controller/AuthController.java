package com.UTN_BECAS.Sistema_Becas.Auth.Controller;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.AuthResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.ChangePasswordRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
        @AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody ChangePasswordRequest request){
            Long usuarioId = ((Usuario) userDetails).getId();
            authService.changePassword(usuarioId, request);
            return ResponseEntity.noContent().build();
    }
}
