package com.UTN_BECAS.Sistema_Becas.Estudiantes.Service;

import com.UTN_BECAS.Sistema_Becas.Core.Exception.ConflictoException;
import com.UTN_BECAS.Sistema_Becas.Core.Exception.RecursoNoEncontradoException;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.DTO.DatosPersonalesResponse;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.Mapper.DatosPersonalesMapper;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.Model.DatosPersonales;
import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Estudiantes.Repository.DatosPersonalesRepository;
import com.UTN_BECAS.Sistema_Becas.Auth.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatosPersonalesServiceImpl implements DatosPersonalesService {

    @Autowired
    private DatosPersonalesRepository datosPersonalesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public DatosPersonalesResponse crear(Long usuarioId, DatosPersonalesRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if(datosPersonalesRepository.findByUsuarioId(usuarioId).isPresent()){
            throw new ConflictoException("El usuario ya tiene datos personales cargados");
        }

        if (datosPersonalesRepository.existsByDni(request.getDni())){
            throw new ConflictoException("El DNI ya esta registrado");
        }

        DatosPersonales datosPersonales = new DatosPersonales();
        datosPersonales.setUsuario(usuario);
        datosPersonales.setDni(request.getDni());
        datosPersonales.setFechaNacimiento(request.getFechaNacimiento());
        datosPersonales.setGenero(request.getGenero());
        datosPersonales.setCelular(request.getCelular());
        datosPersonales.setDomicilioCalle(request.getDomicilioCalle());
        datosPersonales.setDomicilioNumero(request.getDomicilioNumero());
        datosPersonales.setDomicilioPisoDepto(request.getDomicilioPisoDepto());
        datosPersonales.setCodigoPostal(request.getCodigoPostal());
        datosPersonales.setLocalidad(request.getLocalidad());
        datosPersonales.setProvincia(request.getProvincia());
        datosPersonales.setNacionalidad(request.getNacionalidad());

        datosPersonalesRepository.save(datosPersonales);
        return DatosPersonalesMapper.toResponse(datosPersonales);
    }

    @Override
    public DatosPersonalesResponse buscarPorUsuarioId(Long usuarioId) {
        DatosPersonales datosPersonales = datosPersonalesRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Datos personales no encontrados"));
        return DatosPersonalesMapper.toResponse(datosPersonales);
    }

    @Override
    public DatosPersonalesResponse actualizar(Long usuarioId, DatosPersonalesRequest request) {
        DatosPersonales datosPersonales = datosPersonalesRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Datos personales no encontrados"));

        datosPersonales.setDni(request.getDni());
        datosPersonales.setFechaNacimiento(request.getFechaNacimiento());
        datosPersonales.setGenero(request.getGenero());
        datosPersonales.setCelular(request.getCelular());
        datosPersonales.setDomicilioCalle(request.getDomicilioCalle());
        datosPersonales.setDomicilioNumero(request.getDomicilioNumero());
        datosPersonales.setDomicilioPisoDepto(request.getDomicilioPisoDepto());
        datosPersonales.setCodigoPostal(request.getCodigoPostal());
        datosPersonales.setLocalidad(request.getLocalidad());
        datosPersonales.setProvincia(request.getProvincia());
        datosPersonales.setNacionalidad(request.getNacionalidad());

        datosPersonalesRepository.save(datosPersonales);
        return DatosPersonalesMapper.toResponse(datosPersonales);
    }
}
