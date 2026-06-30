package com.UTN_BECAS.Sistema_Becas.Archivos.Service;

import com.UTN_BECAS.Sistema_Becas.Archivos.DTO.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Archivos.Model.TipoArchivo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {

    ArchivoResponse subir(Long postulacionId, TipoArchivo tipoArchivo, MultipartFile file);
    List<ArchivoResponse> listarPorPostulacion(Long postulacionId);
    void eliminar(Long archivoId);
}
