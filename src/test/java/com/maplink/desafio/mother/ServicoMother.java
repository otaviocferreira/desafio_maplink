package com.maplink.desafio.mother;

import com.maplink.desafio.entity.Servico;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServicoMother {

    public static Servico getServico() {
        return Servico.builder()
                .codigo("SVC1234")
                .descricao("Servico para teste")
                .valor(BigDecimal.valueOf(1234))
                .build();
    }
}
