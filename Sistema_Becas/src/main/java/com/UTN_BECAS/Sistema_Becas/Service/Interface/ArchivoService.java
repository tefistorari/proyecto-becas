package com.UTN_BECAS.Sistema_Becas.Service.Interface;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.TipoArchivo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {

    ArchivoResponse subir(Long postulacionId, TipoArchivo tipoArchivo, MultipartFile file);
    List<ArchivoResponse> listarPorPostulacion(Long postulacionId);
    void eliminar(Long archivoId);
}
