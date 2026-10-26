package com.UTN_BECAS.Sistema_Becas.Archivos.Service;

import com.UTN_BECAS.Sistema_Becas.Archivos.DTO.ArchivoDescarga;
import com.UTN_BECAS.Sistema_Becas.Archivos.DTO.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Archivos.Model.TipoArchivo;
import com.UTN_BECAS.Sistema_Becas.Archivos.Mapper.ArchivoMapper;
import com.UTN_BECAS.Sistema_Becas.Archivos.Model.Archivo;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.RecursoNoEncontradoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ReglaDeNegocioException;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.Postulacion;
import com.UTN_BECAS.Sistema_Becas.Archivos.Repository.ArchivoRepository;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository.PostulacionRepository;

   import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ArchivoResponse subir(Long postulacionId, TipoArchivo tipoArchivo, MultipartFile file) {
        Postulacion postulacion = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulacion no encontrada"));

        try {

            // Crear la carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre unico para el archivo
            String nombreUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(nombreUnico);

            // Guardar el archivo en el servidor
            Files.copy(file.getInputStream(), filePath);

            // Guardar en la base de Datos
            Archivo archivo = new Archivo();
            archivo.setPostulacion(postulacion);
            archivo.setTipoArchivo(tipoArchivo);
            archivo.setNombreOriginal(file.getOriginalFilename());
            archivo.setRuta(filePath.toString());

            archivoRepository.save(archivo);
            return ArchivoMapper.toResponse(archivo);
        } catch (IOException e) {
            throw new ReglaDeNegocioException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public List<ArchivoResponse> listarPorPostulacion(Long postulacionId) {
        return archivoRepository.findByPostulacionId(postulacionId)
                .stream()
                .map(ArchivoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long archivoId) {
        Archivo archivo = archivoRepository.findById(archivoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Archivo no encontrado"));

        try {
            // Eliminar el archivo del servidor
            Path filePath = Paths.get(archivo.getRuta());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new ReglaDeNegocioException("Error al eliminar el archivo: " + e.getMessage());
        }

        archivoRepository.delete(archivo);
    }

    @Override
    @Transactional(readOnly = true)
    public ArchivoDescarga descargar(Long archivoId, String emailSolicitante, boolean esAdmin) {
        Archivo archivo = archivoRepository.findById(archivoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Archivo no encontrado"));

        // El ADMIN ve todo; el alumno solo los archivos de sus postulaciones
        if (!esAdmin) {
            String emailDuenio = archivo.getPostulacion().getUsuario().getEmail();
            if (!emailDuenio.equals(emailSolicitante)) {
                throw new AccessDeniedException("No tenés acceso a este archivo");
            }
        }

        Path path = Paths.get(archivo.getRuta());
        if (!Files.exists(path)) {
            throw new RecursoNoEncontradoException("El archivo no se encuentra en el servidor");
        }

        try {
            String contentType = Files.probeContentType(path);
            if (contentType == null)
                contentType = "application/octet-stream";
            return new ArchivoDescarga(new FileSystemResource(path), archivo.getNombreOriginal(), contentType);
        } catch (IOException e) {
            throw new ReglaDeNegocioException("Error al leer el archivo: " + e.getMessage());
        }
    }
}
