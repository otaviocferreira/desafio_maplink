package com.maplink.desafio.mother;

import com.maplink.desafio.entity.Agendamento;
import com.maplink.desafio.entity.Servico;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgendamentoMother {

    public static Agendamento getAgendamento() {
        return Agendamento.builder()
                .dataHora(LocalDateTime.now())
                .observacao("Agendamento para Teste")
                .build();
    }
}
