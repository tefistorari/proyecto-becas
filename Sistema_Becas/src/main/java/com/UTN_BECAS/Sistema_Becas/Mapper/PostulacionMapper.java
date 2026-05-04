package com.UTN_BECAS.Sistema_Becas.Mapper;

import com.UTN_BECAS.Sistema_Becas.DTO.Response.*;
import com.UTN_BECAS.Sistema_Becas.Model.DatosPersonalesHistorial;
import com.UTN_BECAS.Sistema_Becas.Model.Postulacion;

import java.util.List;
import java.util.stream.Collectors;

public class PostulacionMapper {

    public static PostulacionResponse toResponse(Postulacion postulacion) {
        PostulacionResponse response = new PostulacionResponse();
        response.setId(postulacion.getId());
        response.setFechaEnvio(postulacion.getFechaEnvio());
        response.setEstado(postulacion.getEstado());
        response.setConvocatoria(ConvocatoriaMapper.toResponse(postulacion.getConvocatoria()));
        response.setUsuario(UsuarioMapper.toResponse(postulacion.getUsuario()));

        // Datos personales historial
        if (postulacion.getDatosPersonalesHistorial() != null) {
            DatosPersonalesHistorial dp = postulacion.getDatosPersonalesHistorial();
            DatosPersonalesHistorialResponse dpResponse = new DatosPersonalesHistorialResponse();
            dpResponse.setId(dp.getId());
            dpResponse.setNombre(dp.getNombre());
            dpResponse.setApellido(dp.getApellido());
            dpResponse.setDni(dp.getDni());
            dpResponse.setFechaNacimiento(dp.getFechaNacimiento());
            dpResponse.setGenero(dp.getGenero());
            dpResponse.setCelular(dp.getCelular());
            dpResponse.setDomicilioCalle(dp.getDomicilioCalle());
            dpResponse.setDomicilioNumero(dp.getDomicilioNumero());
            dpResponse.setDomicilioPisoDepto(dp.getDomicilioPisoDepto());
            dpResponse.setCodigoPostal(dp.getCodigoPostal());
            dpResponse.setLocalidad(dp.getLocalidad());
            dpResponse.setProvincia(dp.getProvincia());
            dpResponse.setNacionalidad(dp.getNacionalidad());
            response.setDatosPersonalesHistorial(dpResponse);
        }

        // Grupo familiar
        if (postulacion.getGrupoFamiliar() != null) {
            List<GrupoFamiliarResponse> grupoFamiliarResponse = postulacion.getGrupoFamiliar()
                    .stream()
                    .map(gf -> {
                        GrupoFamiliarResponse gfResponse = new GrupoFamiliarResponse();
                        gfResponse.setId(gf.getId());
                        gfResponse.setNombre(gf.getNombre());
                        gfResponse.setApellido(gf.getApellido());
                        gfResponse.setDni(gf.getDni());
                        gfResponse.setParentesco(gf.getParentesco());
                        gfResponse.setOcupacion(gf.getOcupacion());
                        gfResponse.setIngreso(gf.getIngreso());
                        return gfResponse;
                    })
                    .collect(Collectors.toList());
            response.setGrupoFamiliar(grupoFamiliarResponse);
        }

        // Materias a cursar
        if (postulacion.getMateriasACursar() != null) {
            List<MateriasACursarResponse> materiasACursarResponse = postulacion.getMateriasACursar()
                    .stream()
                    .map(mc -> {
                        MateriasACursarResponse mcResponse = new MateriasACursarResponse();
                        mcResponse.setId(mc.getId());
                        mcResponse.setNombreMateria(mc.getNombreMateria());
                        mcResponse.setNivelMateria(mc.getNivelMateria());
                        mcResponse.setRegimenMateria(mc.getRegimenMateria());
                        mcResponse.setAnioMateria(mc.getAnioMateria());
                        return mcResponse;
                    })
                    .collect(Collectors.toList());
            response.setMateriasACursar(materiasACursarResponse);
        }

        // Materias a rendir
        if (postulacion.getMateriasARendir() != null) {
            List<MateriasARendirResponse> materiasARendirResponse = postulacion.getMateriasARendir()
                    .stream()
                    .map(mr -> {
                        MateriasARendirResponse mrResponse = new MateriasARendirResponse();
                        mrResponse.setId(mr.getId());
                        mrResponse.setNombreMateria(mr.getNombreMateria());
                        mrResponse.setNivelMateria(mr.getNivelMateria());
                        mrResponse.setMesMesa(mr.getMesMesa());
                        mrResponse.setAnioMesa(mr.getAnioMesa());
                        return mrResponse;
                    })
                    .collect(Collectors.toList());
            response.setMateriasARendir(materiasARendirResponse);
        }

        return response;
    }

    /*public static PostulacionResponse toResponse(Postulacion postulacion){
        PostulacionResponse response = new PostulacionResponse();
        response.setId(postulacion.getId());
        response.setFechaEnvio(postulacion.getFechaEnvio());
        response.setEstado(postulacion.getEstado());
        response.setConvocatoria(ConvocatoriaMapper.toResponse(postulacion.getConvocatoria()));
        response.setUsuario(UsuarioMapper.toResponse(postulacion.getUsuario()));
        return response;
    }*/
}
