package com.maplink.desafio.exception.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ExceptionResponse {

    private LocalDateTime timestamp;

    private String mensagem;
}
