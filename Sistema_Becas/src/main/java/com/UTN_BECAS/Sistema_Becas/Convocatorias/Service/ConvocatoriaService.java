package com.UTN_BECAS.Sistema_Becas.Convocatorias.Service;

import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaRequest;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.EstadoConvocatoria;

import java.util.List;

public interface ConvocatoriaService {

    ConvocatoriaResponse crear(ConvocatoriaRequest request);
    List<ConvocatoriaResponse> listarTodas();
    List<ConvocatoriaResponse> listarPorEstado(EstadoConvocatoria estado);
    ConvocatoriaResponse buscarPorId(Long id);
    ConvocatoriaResponse actualizar(Long id, ConvocatoriaRequest request);
    ConvocatoriaResponse cambiarEstado(Long id, EstadoConvocatoria estado);
    void eliminar(Long id);
}
