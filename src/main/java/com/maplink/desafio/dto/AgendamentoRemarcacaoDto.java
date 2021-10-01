package com.maplink.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AgendamentoRemarcacaoDto {

    private Long id;

    private LocalDateTime dataHora;
}
