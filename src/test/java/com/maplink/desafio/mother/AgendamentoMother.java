package com.maplink.desafio.mother;

import com.maplink.desafio.dto.AgendamentoDto;
import com.maplink.desafio.dto.AgendamentoRemarcacaoDto;
import com.maplink.desafio.entity.Agendamento;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgendamentoMother {

    public static Agendamento getAgendamento() {
        return Agendamento.builder()
                .dataHora(LocalDateTime.now())
                .observacao("Agendamento para Teste")
                .build();
    }

    public static AgendamentoDto getAgendamentoDto() {
        return AgendamentoDto.builder()
                .dataHora(LocalDateTime.now())
                .observacao("Agendamento para Teste")
                .build();
    }

    public static AgendamentoRemarcacaoDto getAgendamentoRemarcacaoDto() {
        return AgendamentoRemarcacaoDto.builder()
                .dataHora(LocalDateTime.now())
                .build();
    }
}
