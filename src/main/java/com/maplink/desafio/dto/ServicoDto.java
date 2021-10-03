package com.maplink.desafio.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(name = "id", value = "ID do servico", example = "1")
    private Long id;

    @ApiModelProperty(name = "codigo", value = "Código do servico", example = "codigo")
    private String codigo;

    @ApiModelProperty(name = "descricao", value = "Descrição do servico", example = "descrição")
    private String descricao;

    @ApiModelProperty(name = "valor", value = "Valor do servico", example = "154.89")
    private BigDecimal valor;
}
