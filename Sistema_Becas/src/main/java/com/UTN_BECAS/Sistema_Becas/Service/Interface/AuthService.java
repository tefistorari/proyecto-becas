package com.UTN_BECAS.Sistema_Becas.Service.Interface;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.LoginRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.RegisterRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
