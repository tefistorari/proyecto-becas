package com.UTN_BECAS.Sistema_Becas.Estudiantes.Service;

import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesResponse;

public interface DatosPersonalesService {

    DatosPersonalesResponse crear(Long usuarioId, DatosPersonalesRequest request);
    DatosPersonalesResponse buscarPorUsuarioId(Long usuarioId);
    DatosPersonalesResponse actualizar(Long usuarioId, DatosPersonalesRequest request);
}
