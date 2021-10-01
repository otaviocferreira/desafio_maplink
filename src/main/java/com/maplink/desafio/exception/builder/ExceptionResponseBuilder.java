package com.maplink.desafio.exception.builder;

import com.maplink.desafio.exception.enums.ExceptionMensagem;
import com.maplink.desafio.exception.response.ExceptionResponse;
import com.maplink.desafio.exception.utils.MensagemUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ExceptionResponseBuilder {

    private final MensagemUtils mensagemUtils;

    public ExceptionResponse buildExceptionResponse(Throwable cause, ExceptionMensagem mensagem) {
        return ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .mensagem(mensagemUtils.getMensagem(mensagem))
                .build();
    }

}
