package com.UTN_BECAS.Sistema_Becas.Convocatorias.Service;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Becas.Model.Beca;
import com.UTN_BECAS.Sistema_Becas.Becas.Repository.BecaRepository;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaRequest;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.EstadisticasConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.InformeConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Mapper.ConvocatoriaMapper;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Mapper.InformeConvocatoriaMapper;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.Convocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.InformeConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Repository.ConvocatoriaRepository;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Repository.InformeConvocatoriaRepository;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ConflictoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.RecursoNoEncontradoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ReglaDeNegocioException;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

    @Autowired
    private ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private BecaRepository becaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private InformeConvocatoriaRepository informeConvocatoriaRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ConvocatoriaResponse crear(ConvocatoriaRequest request, Long adminId) {
        Beca beca = becaRepository.findById(request.getBecaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada"));

        Usuario admin = usuarioRepository.findById(adminId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (request.getFechaCierre().isBefore(request.getFechaApertura())) {
            throw new ReglaDeNegocioException("La fecha de cierre debe ser posterior a la fecha de apertura");
        }

        Convocatoria convocatoria = new Convocatoria();
        convocatoria.setBeca(beca);
        convocatoria.setAnio(request.getAnio());
        convocatoria.setFechaApertura(request.getFechaApertura());
        convocatoria.setFechaCierre(request.getFechaCierre());
        convocatoria.setDescripcion(request.getDescripcion());
        convocatoria.setEstado(
            request.getFechaApertura().isAfter(LocalDateTime.now())
            ? EstadoConvocatoria.PROGRAMADA
            : EstadoConvocatoria.ABIERTA
        );
        convocatoria.setCupoMaximo(request.getCupoMaximo());
        convocatoria.setCreadoPor(admin);

        convocatoriaRepository.save(convocatoria);
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public List<ConvocatoriaResponse> listarTodas() {
        return convocatoriaRepository.findAll()
                .stream()
                .map(ConvocatoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConvocatoriaResponse> listarPorEstado(EstadoConvocatoria estado) {
        return convocatoriaRepository.findByEstado(estado)
                .stream()
                .map(ConvocatoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ConvocatoriaResponse buscarPorId(Long id) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public ConvocatoriaResponse actualizar(Long id, ConvocatoriaRequest request) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));

        if (request.getFechaCierre().isBefore(request.getFechaApertura())) {
            throw new ReglaDeNegocioException("La fecha de cierre debe ser posterior a la fecha de apertura");
        }

        Beca beca = becaRepository.findById(request.getBecaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Beca no encontrada"));

        convocatoria.setBeca(beca);
        convocatoria.setAnio(request.getAnio());
        convocatoria.setFechaApertura(request.getFechaApertura());
        convocatoria.setFechaCierre(request.getFechaCierre());
        convocatoria.setDescripcion(request.getDescripcion());
        convocatoria.setCupoMaximo(request.getCupoMaximo());

        convocatoriaRepository.save(convocatoria);
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public ConvocatoriaResponse cambiarEstado(Long id, EstadoConvocatoria estado) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));
        convocatoria.setEstado(estado);
        convocatoriaRepository.save(convocatoria);
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public void eliminar(Long id) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));
        convocatoriaRepository.delete(convocatoria);
    }

    @Override
    public EstadisticasConvocatoriaResponse obtenerEstadisticas(Long convocatoriaId) {
        Convocatoria convocatoria = convocatoriaRepository.findById(convocatoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));

        long totalInscriptos = postulacionRepository.countByConvocatoriaId(convocatoriaId);
        long totalAceptados = postulacionRepository.countByConvocatoriaIdAndEstado(convocatoriaId, EstadoPostulacion.ACEPTADO);
        double porcentajeOcupacion = convocatoria.getCupoMaximo() > 0
                ? (totalAceptados * 100.0) / convocatoria.getCupoMaximo()
                : 0;

        EstadisticasConvocatoriaResponse response = new EstadisticasConvocatoriaResponse();
        response.setConvocatoriaId(convocatoriaId);
        response.setNombreBeca(convocatoria.getBeca().getNombre());
        response.setCupoMaximo(convocatoria.getCupoMaximo());
        response.setTotalInscriptos(totalInscriptos);
        response.setTotalAceptados(totalAceptados);
        response.setPorcentajeOcupacion(porcentajeOcupacion);

        return response;
    }

    @Override
    public InformeConvocatoriaResponse subirInforme(Long convocatoriaId, MultipartFile file) {
        Convocatoria convocatoria = convocatoriaRepository.findById(convocatoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));

        if (informeConvocatoriaRepository.findByConvocatoriaId(convocatoriaId).isPresent()) {
            throw new ConflictoException("La convocatoria ya tiene un informe cargado");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nombreUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(nombreUnico);
            Files.copy(file.getInputStream(), filePath);

            InformeConvocatoria informe = new InformeConvocatoria(convocatoria, file.getOriginalFilename(), filePath.toString());
            informeConvocatoriaRepository.save(informe);

            return InformeConvocatoriaMapper.toResponse(informe);
        } catch (IOException e) {
            throw new ReglaDeNegocioException("Error al guardar el informe: " + e.getMessage());
        }
    }

    @Override
    public InformeConvocatoriaResponse obtenerInforme(Long convocatoriaId) {
        InformeConvocatoria informe = informeConvocatoriaRepository.findByConvocatoriaId(convocatoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Informe no encontrado"));
        return InformeConvocatoriaMapper.toResponse(informe);
    }
}