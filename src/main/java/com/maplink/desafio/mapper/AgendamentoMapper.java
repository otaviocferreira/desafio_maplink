package com.maplink.desafio.mapper;

import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.entity.Agendamento;
import org.mapstruct.Mapper;

@Mapper
public interface AgendamentoMapper {

    Agendamento dtoToEntity(AgendamentoDto agendamentoDto);

    AgendamentoDto entityToDto(Agendamento agendamento);
}
