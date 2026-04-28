package com.UTN_BECAS.Sistema_Becas.Service.Impl;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.PostulacionBecaBaseBisRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.PostulacionBecaBinidRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Request.PostulacionRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.CategoriaBinid;
import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Mapper.PostulacionMapper;
import com.UTN_BECAS.Sistema_Becas.Model.*;
import com.UTN_BECAS.Sistema_Becas.Repository.*;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private PostulacionBecaBaseBisRepository baseBisRepository;

    @Autowired
    private PostulacionBecaBinidRepository binidRepository;

    @Override
    public PostulacionResponse crear(Long usuarioId, PostulacionRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Convocatoria convocatoria = convocatoriaRepository.findById(request.getConvocatoriaId())
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));

        if (postulacionRepository.existsByUsuarioIdAndConvocatoriaId(usuarioId, request.getConvocatoriaId())){
            throw new RuntimeException("El usuario ya se registro a esta convocatoria");
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setUsuario(usuario);
        postulacion.setConvocatoria(convocatoria);
        postulacion.setEstado(EstadoPostulacion.PENDIENTE);

        postulacionRepository.save(postulacion);
        return PostulacionMapper.toResponse(postulacion);
    }

    @Override
    public PostulacionResponse completarBaseBis(Long postulacionId, PostulacionBecaBaseBisRequest request) {
        Postulacion postulacion = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));

        PostulacionBecaBaseBis baseBis = new PostulacionBecaBaseBis();
        baseBis.setPostulacion(postulacion);
        baseBis.setIngresoFamiliar(request.getIngresoFamiliar());
        baseBis.setTipoVivienda(request.getTipoVivienda());
        baseBis.setCondicionLaboral(request.getCondicionLaboral());
        baseBis.setCarrera(request.getCarrera());
        baseBis.setSalud(request.getSalud());
        baseBis.setTieneDiscapacidad(request.getTieneDiscapacidad());
        baseBis.setDetalleDiscapacidad(request.getDetalleDiscapacidad());

        baseBisRepository.save(baseBis);
        return PostulacionMapper.toResponse(postulacion);
    }

    @Override
    public PostulacionResponse completarBinid(Long postulacionId, PostulacionBecaBinidRequest request) {
        Postulacion postulacion = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));

        if (request.getCategoriaBinid() == CategoriaBinid.GRADUADO && request.getAnioEgreso() == null){
            throw new RuntimeException("El año de egreso es obligatorio para graduados");
        }

        if (request.getCategoriaBinid() == CategoriaBinid.ESTUDIANTE_AVANZADO && request.getMateriasCursadas() == null){
            throw new RuntimeException("Las materias cursadas son obligatorias para estudiantes avanzados");
        }

        PostulacionBecaBinid binid = new PostulacionBecaBinid();
        binid.setPostulacion(postulacion);
        binid.setCategoriaBinid(request.getCategoriaBinid());
        binid.setCarreraGrado(request.getCarreraGrado());
        binid.setAnioIngreso(request.getAnioIngreso());
        binid.setAnioEgreso(request.getAnioEgreso());
        binid.setMateriasCursadas(request.getMateriasCursadas());
        binid.setPromedioConAplazos(request.getPromedioConAplazos());
        binid.setPromedioSinAplazos(request.getPromedioSinAplazos());
        binid.setPregunta(request.getPregunta());
        binid.setNombreDirectorProyecto(request.getNombreDirectorProyecto());
        binid.setApellidoDirectorProyecto(request.getApellidoDirectorProyecto());

        binidRepository.save(binid);
        return PostulacionMapper.toResponse(postulacion);
    }

    @Override
    public List<PostulacionResponse> listarPorUsuario(Long usuarioId) {
        return postulacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(PostulacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulacionResponse> listarTodas() {
        return postulacionRepository.findAll()
                .stream()
                .map(PostulacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostulacionResponse> listarPorEstado(EstadoPostulacion estado) {
        return postulacionRepository.findByEstado(estado)
                .stream()
                .map(PostulacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PostulacionResponse buscarPorId(Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));
        return PostulacionMapper.toResponse(postulacion);
    }

    @Override
    public PostulacionResponse cambiarEstado(Long id, EstadoPostulacion estado) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));
        postulacion.setEstado(estado);
        postulacionRepository.save(postulacion);
        return PostulacionMapper.toResponse(postulacion);
    }
}
