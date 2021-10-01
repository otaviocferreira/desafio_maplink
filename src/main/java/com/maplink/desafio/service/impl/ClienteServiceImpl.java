package com.maplink.desafio.service.impl;

import com.maplink.desafio.dto.ClienteDto;
import com.maplink.desafio.entity.Cliente;
import com.maplink.desafio.exception.BusinessException;
import com.maplink.desafio.exception.enums.ExceptionMensagem;
import com.maplink.desafio.mapper.ClienteMapper;
import com.maplink.desafio.repository.ClienteRepository;
import com.maplink.desafio.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    @Override
    public ClienteDto salvar(ClienteDto clienteDto) {
        Cliente cliente = clienteMapper.dtoToEntity(clienteDto);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteMapper.entityToDto(clienteSalvo);
    }

    @Override
    public void atualizar(ClienteDto clienteDto) {
        clienteRepository.findById(clienteDto.getId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));
        Cliente cliente = clienteMapper.dtoToEntity(clienteDto);
        clienteRepository.save(cliente);
    }

    @Override
    public void deletar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));
        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteDto obter(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, ExceptionMensagem.RECURSO_NAO_ENCONTRADO, null));
        return clienteMapper.entityToDto(cliente);
    }

    @Override
    public List<ClienteDto> obterTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::entityToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
