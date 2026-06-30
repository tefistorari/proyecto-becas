package com.UTN_BECAS.Sistema_Becas.Convocatorias.Service;

import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaRequest;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.DTO.ConvocatoriaResponse;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.EstadoConvocatoria;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Mapper.ConvocatoriaMapper;
import com.UTN_BECAS.Sistema_Becas.Becas.Model.Beca;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Model.Convocatoria;
import com.UTN_BECAS.Sistema_Becas.Becas.Repository.BecaRepository;
import com.UTN_BECAS.Sistema_Becas.Convocatorias.Repository.ConvocatoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvocatoriaServiceImpl implements ConvocatoriaService {

    @Autowired
    private ConvocatoriaRepository convocatoriaRepository;

    @Autowired
    private BecaRepository becaRepository;

    @Override
    public ConvocatoriaResponse crear(ConvocatoriaRequest request) {
        Beca beca = becaRepository.findById(request.getBecaId())
                .orElseThrow(() -> new RuntimeException("Beca no encontrada"));

        if (request.getFechaCierre().isBefore(request.getFechaApertura())) {
            throw new RuntimeException("La fecha de cierre debe ser posterior a la fecha de apertura");
        }

        Convocatoria convocatoria = new Convocatoria();
        convocatoria.setBeca(beca);
        convocatoria.setAnio(request.getAnio());
        convocatoria.setFechaApertura(request.getFechaApertura());
        convocatoria.setFechaCierre(request.getFechaCierre());
        convocatoria.setDescripcion(request.getDescripcion());
        convocatoria.setEstado(EstadoConvocatoria.ABIERTA);

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
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public ConvocatoriaResponse actualizar(Long id, ConvocatoriaRequest request) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));

        if (request.getFechaCierre().isBefore(request.getFechaApertura())) {
            throw new RuntimeException("La fecha de cierre debe ser posterior a la fecha de apertura");
        }

        Beca beca = becaRepository.findById(request.getBecaId())
                .orElseThrow(() -> new RuntimeException("Beca no encontrada"));

        convocatoria.setBeca(beca);
        convocatoria.setAnio(request.getAnio());
        convocatoria.setFechaApertura(request.getFechaApertura());
        convocatoria.setFechaCierre(request.getFechaCierre());
        convocatoria.setDescripcion(request.getDescripcion());

        convocatoriaRepository.save(convocatoria);
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public ConvocatoriaResponse cambiarEstado(Long id, EstadoConvocatoria estado) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));
        convocatoria.setEstado(estado);
        convocatoriaRepository.save(convocatoria);
        return ConvocatoriaMapper.toResponse(convocatoria);
    }

    @Override
    public void eliminar(Long id) {
        Convocatoria convocatoria = convocatoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convocatoria no encontrada"));
        convocatoriaRepository.delete(convocatoria);
    }
}
