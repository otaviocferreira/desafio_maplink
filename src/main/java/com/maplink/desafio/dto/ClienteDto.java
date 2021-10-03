package com.maplink.desafio.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ClienteDto {

    @ApiModelProperty(name = "id", value = "ID do cliente", example = "1")
    private Long id;

    @ApiModelProperty(name = "nome", value = "Nome do cliente", example = "nome")
    private String nome;

    @ApiModelProperty(name = "cpf", value = "CPF do cliente", example = "12345678909")
    private String cpf;
}
