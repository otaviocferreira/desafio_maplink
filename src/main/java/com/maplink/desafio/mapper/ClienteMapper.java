package com.maplink.desafio.mapper;

import com.maplink.desafio.dto.ClienteDto;
import com.maplink.desafio.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper
public interface ClienteMapper {

    Cliente dtoToEntity(ClienteDto clienteDto);

    ClienteDto entityToDto(Cliente cliente);
}
