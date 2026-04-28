package com.UTN_BECAS.Sistema_Becas.Service.Impl;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.DatosPersonalesRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.DatosPersonalesResponse;
import com.UTN_BECAS.Sistema_Becas.Mapper.DatosPersonalesMapper;
import com.UTN_BECAS.Sistema_Becas.Model.DatosPersonales;
import com.UTN_BECAS.Sistema_Becas.Model.Usuario;
import com.UTN_BECAS.Sistema_Becas.Repository.DatosPersonalesRepository;
import com.UTN_BECAS.Sistema_Becas.Repository.UsuarioRepository;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.DatosPersonalesService;
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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(datosPersonalesRepository.findByUsuarioId(usuarioId).isPresent()){
            throw new RuntimeException("El usuario ya tiene datos personales cargados");
        }

        if (datosPersonalesRepository.existsByDni(request.getDni())){
            throw new RuntimeException("El DNI ya esta registrado");
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
                .orElseThrow(() -> new RuntimeException("Datos personales no encontrados"));
        return DatosPersonalesMapper.toResponse(datosPersonales);
    }

    @Override
    public DatosPersonalesResponse actualizar(Long usuarioId, DatosPersonalesRequest request) {
        DatosPersonales datosPersonales = datosPersonalesRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Datos personales no encontrados"));

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
