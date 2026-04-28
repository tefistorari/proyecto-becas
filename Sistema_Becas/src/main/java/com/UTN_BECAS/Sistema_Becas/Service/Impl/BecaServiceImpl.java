package com.UTN_BECAS.Sistema_Becas.Service.Impl;

import com.UTN_BECAS.Sistema_Becas.DTO.Request.BecaRequest;
import com.UTN_BECAS.Sistema_Becas.DTO.Response.BecaResponse;
import com.UTN_BECAS.Sistema_Becas.Mapper.BecaMapper;
import com.UTN_BECAS.Sistema_Becas.Model.Beca;
import com.UTN_BECAS.Sistema_Becas.Repository.BecaRepository;
import com.UTN_BECAS.Sistema_Becas.Service.Interface.BecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BecaServiceImpl implements BecaService {

    @Autowired
    private BecaRepository becaRepository;

    @Override
    public BecaResponse crear(BecaRequest request){
        Beca beca = new Beca();
        beca.setNombre(request.getNombre());
        beca.setDescripcion(request.getDescripcion());
        beca.setTipoBeca(request.getTipoBeca());
        beca.setRequiereIngenieria(request.getRequiereIngenieria());
        becaRepository.save(beca);
        return BecaMapper.toResponse(beca);
    }

    @Override
    public List<BecaResponse> listarTodas() {
        return becaRepository.findAll()
                .stream()
                .map(BecaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BecaResponse buscarPorId(Long id) {
        Beca beca = becaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beca no encontrada"));
        return BecaMapper.toResponse(beca);
    }

    @Override
    public BecaResponse actualizar(Long id, BecaRequest request) {
        Beca beca = becaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beca no encontrada"));
        beca.setNombre(request.getNombre());
        beca.setDescripcion(request.getDescripcion());
        beca.setTipoBeca(request.getTipoBeca());
        beca.setRequiereIngenieria(request.getRequiereIngenieria());
        becaRepository.save(beca);
        return BecaMapper.toResponse(beca);
    }

    @Override
    public void eliminar(Long id) {
        Beca beca = becaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beca no encontrada"));
        becaRepository.delete(beca);
    }
}
