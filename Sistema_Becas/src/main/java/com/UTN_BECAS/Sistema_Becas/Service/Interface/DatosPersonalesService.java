package com.UTN_BECAS.Sistema_Becas.Service.Interface;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.DatosPersonalesResponse;

public interface DatosPersonalesService {

    DatosPersonalesResponse crear(Long usuarioId, DatosPersonalesRequest request);
    DatosPersonalesResponse buscarPorUsuarioId(Long usuarioId);
    DatosPersonalesResponse actualizar(Long usuarioId, DatosPersonalesRequest request);
}
