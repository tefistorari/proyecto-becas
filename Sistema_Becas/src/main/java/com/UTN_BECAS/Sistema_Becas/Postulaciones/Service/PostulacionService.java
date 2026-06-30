package com.UTN_BECAS.Sistema_Becas.Postulaciones.Service;

import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionBecaBaseBisRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionBecaBinidRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;

import java.util.List;

public interface PostulacionService {

    PostulacionResponse crear(Long usuarioId, PostulacionRequest request);
    PostulacionResponse completarBaseBis(Long postulacionId, PostulacionBecaBaseBisRequest request);
    PostulacionResponse completarBinid(Long postulacionId, PostulacionBecaBinidRequest request);
    List<PostulacionResponse> listarPorUsuario(Long usuarioId);
    List<PostulacionResponse> listarTodas();
    List<PostulacionResponse> listarPorEstado(EstadoPostulacion estado);
    PostulacionResponse buscarPorId(Long id);
    PostulacionResponse cambiarEstado(Long id, EstadoPostulacion estado);
}
