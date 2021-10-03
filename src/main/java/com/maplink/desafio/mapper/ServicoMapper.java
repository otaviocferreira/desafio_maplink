package com.maplink.desafio.mapper;

import com.maplink.desafio.dto.ServicoDto;
import com.maplink.desafio.entity.Servico;
import org.mapstruct.Mapper;

@Mapper
public interface ServicoMapper {

    Servico dtoToEntity(ServicoDto servicoDto);

    ServicoDto entityToDto(Servico servico);
}
