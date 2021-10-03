package com.maplink.desafio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(name = "id", value = "ID do agendamento", example = "1")
    private Long id;

    @ApiModelProperty(name = "dataHora", value = "Data e hora do agendamento", example = "20/05/2021 13:45")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHora;

    @ApiModelProperty(name = "observacao", value = "Observação do agendamento", example = "observacao")
    private String observacao;

    @ApiModelProperty(name = "cliente", value = "Cliente do agendamento")
    private ClienteDto cliente;

    @ApiModelProperty(name = "servico", value = "Serviço do agendamento")
    private ServicoDto servico;
}
