package com.maplink.desafio.dto;

import com.maplink.desafio.entity.Cliente;
import com.maplink.desafio.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AgendamentoDto {

    private Long id;

    private LocalDateTime dataHora;

    private String observacao;

    private ClienteDto cliente;

    private ServicoDto servico;
}
