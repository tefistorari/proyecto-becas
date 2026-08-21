package com.UTN_BECAS.Sistema_Becas.Postulaciones.Service;

import com.UTN_BECAS.Sistema_Becas.Archivos.Model.TipoArchivo;
import com.UTN_BECAS.Sistema_Becas.Archivos.Repository.ArchivoRepository;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.Convocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Repository.ConvocatoriaRepository;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ConflictoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.RecursoNoEncontradoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.ReglaDeNegocioException;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionBaseBisUnificadoRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionBinidUnificadoRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.DTO.PostulacionResponse;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.CategoriaBinid;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Enums.EstadoPostulacion;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.GrupoFamiliarRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.MateriasACursarRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.MateriasARendirRequest;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Mapper.PostulacionMapper;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Model.*;
import com.UTN_BECAS.Sistema_Becas.Postulaciones.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Autowired
    private HistorialBecaBinidRepository historialBecaBinidRepository;

    @Autowired
    private ArchivoRepository archivoRepository;

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
        postulacion.setEstado(EstadoPostulacion.BORRADOR);
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
        historial.setDomicilioFamiliarDistinto(dp.getDomicilioFamiliarDistinto());
        if(Boolean.TRUE.equals(dp.getDomicilioFamiliarDistinto())) {
            historial.setDomicilioFamiliarCalle(dp.getDomicilioFamiliarCalle());
            historial.setDomicilioFamiliarNumero(dp.getDomicilioFamiliarNumero());
            historial.setDomicilioFamiliarPisoDepto(dp.getDomicilioFamiliarPisoDepto());
            historial.setDomicilioFamiliarCodigoPostal(dp.getDomicilioFamiliarCodigoPostal());
            historial.setDomicilioFamiliarLocalidad(dp.getDomicilioFamiliarLocalidad());
            historial.setDomicilioFamiliarProvincia(dp.getDomicilioFamiliarProvincia());
        }
        datosPersonalesHistorialRepository.save(historial);
        postulacion.setDatosPersonalesHistorial(historial);

        // 3.Datos especificos BASE-BIS
        PostulacionBecaBaseBis baseBis = new PostulacionBecaBaseBis();
        baseBis.setPostulacion(postulacion);
        baseBis.setTipoVivienda(request.getTipoVivienda());
        baseBis.setCondicionLaboral(request.getCondicionLaboral());
        baseBis.setCarrera(request.getCarrera());
        baseBis.setSalud(request.getSalud());
        baseBis.setTieneCondicionSalud(request.getTieneCondicionSalud());
        baseBis.setDetalleCondicionSalud(request.getDetalleCondicionSalud());
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
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Convocatoria convocatoria = convocatoriaRepository.findById(request.getConvocatoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Convocatoria no encontrada"));

        if(convocatoria.getEstado() != EstadoConvocatoria.ABIERTA) {
            throw new ReglaDeNegocioException("La convocatoria no está abierta");
        }

        if(postulacionRepository.existsByUsuarioIdAndConvocatoriaId(usuarioId, request.getConvocatoriaId())) {
            throw new ConflictoException("Ya te postulaste a esta convocatoria");
        }

        if((request.getCategoriaBinid() == CategoriaBinid.GRADUADO ||
                request.getCategoriaBinid() == CategoriaBinid.EGRESADO) &&
                request.getAnioEgreso() == null) {
            throw new ReglaDeNegocioException("El año del egreso es obligatorio para egresados y graduados");
        }

        if(request.getAnioEgreso() != null) {
            int anioActual = LocalDate.now().getYear();
            if (request.getAnioEgreso() < anioActual - 3) {
                throw new ReglaDeNegocioException("Solo pueden postularse hasta 3 años después de recibido el título");
            }
        }

        if(request.getCategoriaBinid() == CategoriaBinid.ESTUDIANTE_AVANZADO && request.getMateriasCursadas() == null) {
            throw new ReglaDeNegocioException("Las materias cursadas son obligatorias para estudiantes avanzados");
        }

        long renovaciones = historialBecaBinidRepository.countByUsuarioId(usuarioId);
        if(renovaciones >= 2) {
            throw new ReglaDeNegocioException("Superaste el límite de renovaciones BINID");
        }

        // 1. Crear postulacion
        Postulacion postulacion = new Postulacion();
        postulacion.setUsuario(usuario);
        postulacion.setConvocatoria(convocatoria);
        postulacion.setEstado(EstadoPostulacion.BORRADOR);
        postulacionRepository.save(postulacion);

        // 2. Datos personales historial
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
        historial.setDomicilioFamiliarDistinto(dp.getDomicilioFamiliarDistinto());
        if(Boolean.TRUE.equals(dp.getDomicilioFamiliarDistinto())) {
            historial.setDomicilioFamiliarCalle(dp.getDomicilioFamiliarCalle());
            historial.setDomicilioFamiliarNumero(dp.getDomicilioFamiliarNumero());
            historial.setDomicilioFamiliarPisoDepto(dp.getDomicilioFamiliarPisoDepto());
            historial.setDomicilioFamiliarCodigoPostal(dp.getDomicilioFamiliarCodigoPostal());
            historial.setDomicilioFamiliarLocalidad(dp.getDomicilioFamiliarLocalidad());
            historial.setDomicilioFamiliarProvincia(dp.getDomicilioFamiliarProvincia());
        }
        datosPersonalesHistorialRepository.save(historial);
        postulacion.setDatosPersonalesHistorial(historial);

        // 3. Datos especificos BINID
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
        binid.setAccedioABecaBinidAnterior(request.getAccedioABecaBinidAnterior());
        binidRepository.save(binid);

        // 4. Guardar historial BINID
        HistorialBecaBinid historialBinid = new HistorialBecaBinid(usuario, postulacion);
        historialBecaBinidRepository.save(historialBinid);

        return PostulacionMapper.toResponse(postulacion, binid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionResponse> listarPorUsuario(Long usuarioId) {
        return postulacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(p -> {
                    PostulacionBecaBaseBis baseBis = baseBisRepository.findByPostulacionId(p.getId()).orElse(null);
                    PostulacionBecaBinid binid = binidRepository.findByPostulacionId(p.getId()).orElse(null);
                    if (baseBis != null) return PostulacionMapper.toResponse(p, baseBis);
                    if (binid != null) return PostulacionMapper.toResponse(p, binid);
                    return PostulacionMapper.toResponse(p);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionResponse> listarTodas() {
        return postulacionRepository.findAll()
                .stream()
                .map(p -> {
                    PostulacionBecaBaseBis baseBis = baseBisRepository.findByPostulacionId(p.getId()).orElse(null);
                    PostulacionBecaBinid binid = binidRepository.findByPostulacionId(p.getId()).orElse(null);
                    if (baseBis != null) return PostulacionMapper.toResponse(p, baseBis);
                    if (binid != null) return PostulacionMapper.toResponse(p, binid);
                    return PostulacionMapper.toResponse(p);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostulacionResponse> listarPorEstado(EstadoPostulacion estado) {
        return postulacionRepository.findByEstado(estado)
                .stream()
                .map(p -> {
                    PostulacionBecaBaseBis baseBis = baseBisRepository.findByPostulacionId(p.getId()).orElse(null);
                    PostulacionBecaBinid binid = binidRepository.findByPostulacionId(p.getId()).orElse(null);
                    if (baseBis != null) return PostulacionMapper.toResponse(p, baseBis);
                    if (binid != null) return PostulacionMapper.toResponse(p, binid);
                    return PostulacionMapper.toResponse(p);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostulacionResponse buscarPorId(Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulacion no encontrada"));
        PostulacionBecaBaseBis baseBis = baseBisRepository.findByPostulacionId(id).orElse(null);
        PostulacionBecaBinid binid = binidRepository.findByPostulacionId(id).orElse(null);
        if (baseBis != null) return PostulacionMapper.toResponse(postulacion, baseBis);
        if (binid != null) return PostulacionMapper.toResponse(postulacion, binid);
        return PostulacionMapper.toResponse(postulacion);
    }

    @Override
    public PostulacionResponse cambiarEstado(Long id, EstadoPostulacion estado) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulacion no encontrada"));
        postulacion.setEstado(estado);
        postulacionRepository.save(postulacion);
        return PostulacionMapper.toResponse(postulacion);
    }

        @Override
        @Transactional
        public PostulacionResponse finalizar(Long postulacionId) {
            Postulacion postulacion = postulacionRepository.findById(postulacionId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Postulacion no encontrada"));

            if(postulacion.getEstado() != EstadoPostulacion.BORRADOR) {
                throw new ReglaDeNegocioException("Solo se pueden finalizar postulaciones en estado BORRADOR");
            }

            List<TipoArchivo> archivosSubidos = archivoRepository.findByPostulacionId(postulacionId)
                    .stream()
                    .map(a -> a.getTipoArchivo())
                    .collect(Collectors.toList());

            boolean esBaseBis = baseBisRepository.findByPostulacionId(postulacionId).isPresent();
            boolean esBinid = binidRepository.findByPostulacionId(postulacionId).isPresent();

            if(esBaseBis) {
                if(!archivosSubidos.contains(TipoArchivo.DNI)) {
                    throw new ReglaDeNegocioException("Falta el archivo DNI obligatorio");
                }
            }

            if(esBinid) {
                List<TipoArchivo> obligatoriosBinid = List.of(
                        TipoArchivo.DNI,
                        TipoArchivo.NOTA_AVAL_DIRECTOR,
                        TipoArchivo.CARTA_MOTIVACION,
                        TipoArchivo.ANALITICO,
                        TipoArchivo.CERTIFICADO_ALUMNO_REGULAR
                );

                List<TipoArchivo> faltantes = obligatoriosBinid.stream()
                        .filter(tipo -> !archivosSubidos.contains(tipo))
                        .collect(Collectors.toList());

                if(!faltantes.isEmpty()) {
                    throw new ReglaDeNegocioException("Faltan los siguientes archivos obligatorios: " + faltantes);
                }
            }

            postulacion.setEstado(EstadoPostulacion.PENDIENTE);
            postulacionRepository.save(postulacion);

            if(esBaseBis) {
                PostulacionBecaBaseBis baseBis = baseBisRepository.findByPostulacionId(postulacionId).orElse(null);
                return PostulacionMapper.toResponse(postulacion, baseBis);
            }

            if(esBinid) {
                PostulacionBecaBinid binid = binidRepository.findByPostulacionId(postulacionId).orElse(null);
                return PostulacionMapper.toResponse(postulacion, binid);
            }

            return PostulacionMapper.toResponse(postulacion);
        }
}
