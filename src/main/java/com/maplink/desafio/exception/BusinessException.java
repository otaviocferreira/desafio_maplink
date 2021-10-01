package com.maplink.desafio.exception;

import com.maplink.desafio.exception.enums.ExceptionMensagem;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionMensagem mensagem;
    private final HttpStatus status;

    public BusinessException(HttpStatus status, ExceptionMensagem mensagem, Throwable cause) {
        super(mensagem.getMensagem(), cause);
        this.mensagem = mensagem;
        this.status = status;
    }
}
