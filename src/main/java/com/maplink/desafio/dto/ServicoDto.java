package com.maplink.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ServicoDto {

    private Long id;

    private String codigo;

    private String descricao;

    private BigDecimal valor;
}
