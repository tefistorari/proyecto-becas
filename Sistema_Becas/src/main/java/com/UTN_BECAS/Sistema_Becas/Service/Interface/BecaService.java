package com.UTN_BECAS.Sistema_Becas.Service.Interface;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.BecaRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.BecaResponse;

import java.util.List;

public interface BecaService {

    BecaResponse crear(BecaRequest request);
    List<BecaResponse> listarTodas();
    BecaResponse buscarPorId(Long id);
    BecaResponse actualizar(Long id, BecaRequest request);
    void eliminar(Long id);
}
