package com.maplink.desafio.service;

import com.maplink.desafio.dto.ServicoDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface ServicoService {

    ServicoDto salvar(ServicoDto servicoDto);

    void atualizar(ServicoDto servicoDto);

    void deletar(Long id);

    ServicoDto obter(Long id);

    List<ServicoDto> obterTodos();
}
