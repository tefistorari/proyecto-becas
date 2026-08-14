package com.UTN_BECAS.Sistema_Becas.Auth.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.AuthResponse;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.ChangePasswordRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void changePassword(Long usuarioId, ChangePasswordRequest request);
}
