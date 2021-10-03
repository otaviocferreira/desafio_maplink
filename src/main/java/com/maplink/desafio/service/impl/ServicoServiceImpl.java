package com.maplink.desafio.service.impl;

import com.maplink.desafio.dto.ServicoDto;
import com.maplink.desafio.entity.Servico;
import com.maplink.desafio.exception.BusinessException;
import com.maplink.desafio.exception.enums.ExceptionMensagem;
import com.maplink.desafio.mapper.ServicoMapper;
import com.maplink.desafio.repository.ServicoRepository;
import com.maplink.desafio.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository servicoRepository;

    private final ServicoMapper servicoMapper;

    @Override
    public ServicoDto salvar(ServicoDto servicoDto) {
        Servico servico = servicoMapper.dtoToEntity(servicoDto);
        Servico servicoSalvo = servicoRepository.save(servico);
        return servicoMapper.entityToDto(servicoSalvo);
    }

    @Override
    public void atualizar(ServicoDto servicoDto) {
        servicoRepository.findById(servicoDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));
        Servico servico = servicoMapper.dtoToEntity(servicoDto);
        servicoRepository.save(servico);
    }

    @Override
    public void deletar(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));
        servicoRepository.delete(servico);
    }

    @Override
    public ServicoDto obter(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));
        return servicoMapper.entityToDto(servico);
    }

    @Override
    public List<ServicoDto> obterTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(servicoMapper::entityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
