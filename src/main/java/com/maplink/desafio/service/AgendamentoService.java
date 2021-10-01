package com.maplink.desafio.service;

import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.dto.AgendamentoRemarcacaoDto;
import com.maplink.desafio.dto.ServicoDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface AgendamentoService {

    AgendamentoDto salvar(AgendamentoDto agendamentoDto);

    AgendamentoDto remarcar(AgendamentoRemarcacaoDto agendamentoRemarcacaoDto);

    List<AgendamentoDto> obterTodos();
}
