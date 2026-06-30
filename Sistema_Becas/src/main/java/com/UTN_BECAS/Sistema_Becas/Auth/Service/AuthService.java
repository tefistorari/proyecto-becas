package com.UTN_BECAS.Sistema_Becas.Auth.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.DTO.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.Auth.DTO.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
