package com.UTN_BECAS.Sistema_Becas.Service.Impl;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.ArchivoResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.TipoArchivo;
import com.UTN_BECAS.Sistema_Becas.Mapper.ArchivoMapper;
import com.UTN_BECAS.Sistema_Becas.Model.Archivo;
import com.UTN_BECAS.Sistema_Becas.Model.Postulacion;
import com.UTN_BECAS.Sistema_Becas.Repository.ArchivoRepository;
import com.UTN_BECAS.Sistema_Becas.Repository.PostulacionRepository;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));

        try{

            //Crear la carpeta si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            //Generar nombre unico para el archivo
            String nombreUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(nombreUnico);

            //Guardar el archivo en el servidor
            Files.copy(file.getInputStream(), filePath);

            //Guardar en la base de Datos
            Archivo archivo = new Archivo();
            archivo.setPostulacion(postulacion);
            archivo.setTipoArchivo(tipoArchivo);
            archivo.setNombreOriginal(file.getOriginalFilename());
            archivo.setRuta(filePath.toString());

            archivoRepository.save(archivo);
            return ArchivoMapper.toResponse(archivo);
        }catch (IOException e){
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
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
                .orElseThrow(() -> new RuntimeException("Archivo no encontrado"));

        try{
            //Eliminar el archivo del servidor
            Path filePath = Paths.get(archivo.getRuta());
            Files.deleteIfExists(filePath);
        }catch (IOException e ){
            throw new RuntimeException("Error al eliminar el archivo: " + e.getMessage());
        }

        archivoRepository.delete(archivo);
    }
}
