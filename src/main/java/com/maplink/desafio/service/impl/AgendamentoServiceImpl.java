package com.maplink.desafio.service.impl;

import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.dto.AgendamentoRemarcacaoDto;
import com.maplink.desafio.dto.ServicoDto;
import com.maplink.desafio.entity.Agendamento;
import com.maplink.desafio.entity.Servico;
import com.maplink.desafio.exception.BusinessException;
import com.maplink.desafio.exception.enums.ExceptionMensagem;
import com.maplink.desafio.mapper.AgendamentoMapper;
import com.maplink.desafio.repository.AgendamentoRepository;
import com.maplink.desafio.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    private final AgendamentoMapper agendamentoMapper;

    @Override
    public AgendamentoDto salvar(AgendamentoDto agendamentoDto) {
        Agendamento agendamento = agendamentoMapper.dtoToEntity(agendamentoDto);
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.entityToDto(agendamentoSalvo);
    }

    @Override
    public AgendamentoDto remarcar(AgendamentoRemarcacaoDto agendamentoRemarcacaoDto) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoRemarcacaoDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));

        agendamento.setDataHora(agendamentoRemarcacaoDto.getDataHora());

        Agendamento servicoSalvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.entityToDto(servicoSalvo);
    }

    @Override
    public List<AgendamentoDto> obterTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(agendamentoMapper::entityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
