package com.UTN_BECAS.Sistema_Becas.Becas.Service;

import com.UTN_BECAS.Sistema_Becas.Becas.DTO.BecaRequest;
import com.UTN_BECAS.Sistema_Becas.Becas.DTO.BecaResponse;

import java.util.List;

public interface BecaService {

    BecaResponse crear(BecaRequest request);
    List<BecaResponse> listarTodas();
    BecaResponse buscarPorId(Long id);
    BecaResponse actualizar(Long id, BecaRequest request);
    void eliminar(Long id);
}
