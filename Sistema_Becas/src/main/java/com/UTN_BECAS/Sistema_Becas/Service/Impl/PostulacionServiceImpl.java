package com.UTN_BECAS.Sistema_Becas.Service.Impl;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.*;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Enum.CategoriaBinid;
import com.UTN_BECAS.Sistema_Becas.Enum.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Mapper.PostulacionMapper;
import com.UTN_BECAS.Sistema_Becas.Model.*;
import com.UTN_BECAS.Sistema_Becas.Repository.*;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private DatosPersonalesHistorialRepository datosPersonalesHistorialRepository;

    @Autowired
    private GrupoFamiliarRepository grupoFamiliarRepository;

    @Autowired
    private MateriasACursarRepository materiasACursarRepository;

    @Autowired
    private MateriasARendirRepository materiasARendirRepository;

    @Override
    @Transactional
    public PostulacionResponse postularBaseBis(Long usuarioId, PostulacionBaseBisUnificadoRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Convocatoria convocatoria = convocatoriaRepository.findById(request.getConvocatoriaId())
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));

        if (convocatoria.getEstado() != EstadoConvocatoria.ABIERTA) {
            throw new RuntimeException("La convocatoria no está abierta");
        }

        if (postulacionRepository.existsByUsuarioIdAndConvocatoriaId(usuarioId, request.getConvocatoriaId())) {
            throw new RuntimeException("Ya te postulaste a esta convocatoria");
        }

        //Crear postulacion
        Postulacion postulacion = new Postulacion();
        postulacion.setUsuario(usuario);
        postulacion.setConvocatoria(convocatoria);
        postulacion.setEstado(EstadoPostulacion.PENDIENTE);
        postulacionRepository.save(postulacion);

        // 2.Datos personales historial
        DatosPersonalesRequest dp = request.getDatosPersonales();
        DatosPersonalesHistorial historial = new DatosPersonalesHistorial();
        historial.setPostulacion(postulacion);
        historial.setNombre(usuario.getNombre());
        historial.setApellido(usuario.getApellido());
        historial.setDni(dp.getDni());
        historial.setFechaNacimiento(dp.getFechaNacimiento());
        historial.setGenero(dp.getGenero());
        historial.setCelular(dp.getCelular());
        historial.setDomicilioCalle(dp.getDomicilioCalle());
        historial.setDomicilioNumero(dp.getDomicilioNumero());
        historial.setDomicilioPisoDepto(dp.getDomicilioPisoDepto());
        historial.setCodigoPostal(dp.getCodigoPostal());
        historial.setLocalidad(dp.getLocalidad());
        historial.setProvincia(dp.getProvincia());
        historial.setNacionalidad(dp.getNacionalidad());
        datosPersonalesHistorialRepository.save(historial);
        postulacion.setDatosPersonalesHistorial(historial);

        // 3.Datos especificos BASE-BIS
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

        // 4.grupo familiar
        if(request.getGrupoFamiliar() != null) {
            for (GrupoFamiliarRequest gf : request.getGrupoFamiliar()) {
                GrupoFamiliar familiar = new GrupoFamiliar();
                familiar.setPostulacion(postulacion);
                familiar.setNombre(gf.getNombre());
                familiar.setApellido(gf.getApellido());
                familiar.setDni(gf.getDni());
                familiar.setParentesco(gf.getParentesco());
                familiar.setOcupacion(gf.getOcupacion());
                familiar.setIngreso(gf.getIngreso());
                grupoFamiliarRepository.save(familiar);
                postulacion.getGrupoFamiliar().add(familiar);
            }
        }

        // 5.Materias a cursar
        if (request.getMateriasACursar() != null) {
            for (MateriasACursarRequest mc : request.getMateriasACursar()) {
                MateriasACursar materia = new MateriasACursar();
                materia.setPostulacion(postulacion);
                materia.setNombreMateria(mc.getNombreMateria());
                materia.setNivelMateria(mc.getNivelMateria());
                materia.setRegimenMateria(mc.getRegimenMateria());
                materia.setAnioMateria(mc.getAnioMateria());
                materiasACursarRepository.save(materia);
                postulacion.getMateriasACursar().add(materia);
            }
        }

        // 6. Materias a rendir
        if (request.getMateriasARendir() != null) {
            for (MateriasARendirRequest mr : request.getMateriasARendir()) {
                MateriasARendir materia = new MateriasARendir();
                materia.setPostulacion(postulacion);
                materia.setNombreMateria(mr.getNombreMateria());
                materia.setNivelMateria(mr.getNivelMateria());
                materia.setMesMesa(mr.getMesMesa());
                materia.setAnioMesa(mr.getAnioMesa());
                materiasARendirRepository.save(materia);
                postulacion.getMateriasARendir().add(materia);
            }
        }

        return PostulacionMapper.toResponse(postulacion, baseBis);
    }

    @Override
    @Transactional
    public PostulacionResponse postularBinid(Long usuarioId, PostulacionBinidUnificadoRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Convocatoria convocatoria = convocatoriaRepository.findById(request.getConvocatoriaId())
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));

        if(convocatoria.getEstado() != EstadoConvocatoria.ABIERTA) {
            throw new RuntimeException("La convocatoria no está abierta");
        }

        if(postulacionRepository.existsByUsuarioIdAndConvocatoriaId(usuarioId, request.getConvocatoriaId())) {
            throw new RuntimeException("Ya te postulaste a esta convocatoria");
        }

        if(request.getCategoriaBinid() == CategoriaBinid.GRADUADO && request.getAnioEgreso() == null) {
            throw new RuntimeException("El año del egreso es obligatorio para graduados");
        }

        if(request.getCategoriaBinid() == CategoriaBinid.ESTUDIANTE_AVANZADO && request.getMateriasCursadas() == null) {
            throw new RuntimeException("Las materias cursadas son obligatorias para estudiantes avanzados");
        }

        // 1.Crear postulacion
        Postulacion postulacion = new Postulacion();
        postulacion.setUsuario(usuario);
        postulacion.setConvocatoria(convocatoria);
        postulacion.setEstado(EstadoPostulacion.PENDIENTE);
        postulacionRepository.save(postulacion);

        // 2.Datos personales historai
        DatosPersonalesRequest dp = request.getDatosPersonales();
        DatosPersonalesHistorial historial = new DatosPersonalesHistorial();
        historial.setPostulacion(postulacion);
        historial.setNombre(usuario.getNombre());
        historial.setApellido(usuario.getApellido());
        historial.setDni(dp.getDni());
        historial.setFechaNacimiento(dp.getFechaNacimiento());
        historial.setGenero(dp.getGenero());
        historial.setCelular(dp.getCelular());
        historial.setDomicilioCalle(dp.getDomicilioCalle());
        historial.setDomicilioNumero(dp.getDomicilioNumero());
        historial.setDomicilioPisoDepto(dp.getDomicilioPisoDepto());
        historial.setCodigoPostal(dp.getCodigoPostal());
        historial.setLocalidad(dp.getLocalidad());
        historial.setProvincia(dp.getProvincia());
        historial.setNacionalidad(dp.getNacionalidad());
        datosPersonalesHistorialRepository.save(historial);
        postulacion.setDatosPersonalesHistorial(historial);

        // 3.Datos especificos BINID
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

        return PostulacionMapper.toResponse(postulacion, binid);
    }
    /*@Override
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
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionResponse> listarPorUsuario(Long usuarioId) {
        return postulacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(PostulacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionResponse> listarTodas() {
        return postulacionRepository.findAll()
                .stream()
                .map(PostulacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionResponse> listarPorEstado(EstadoPostulacion estado) {
        return postulacionRepository.findByEstado(estado)
                .stream()
                .map(PostulacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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
