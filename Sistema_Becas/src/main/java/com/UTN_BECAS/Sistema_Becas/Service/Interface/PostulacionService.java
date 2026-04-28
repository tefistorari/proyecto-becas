package com.UTN_BECAS.Sistema_Becas.Service.Interface;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.PostulacionBecaBaseBisRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.PostulacionBecaBinidRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.PostulacionRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;

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
