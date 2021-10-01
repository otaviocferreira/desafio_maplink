package com.maplink.desafio.service;

import com.maplink.desafio.dto.ClienteDto;

import java.util.List;

public interface ClienteService {

    ClienteDto salvar(ClienteDto clienteDto);

    void atualizar(ClienteDto clienteDto);

    void deletar(Long id);

    ClienteDto obter(Long id);

    List<ClienteDto> obterTodos();
}
