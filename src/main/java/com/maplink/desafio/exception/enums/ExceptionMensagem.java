package com.maplink.desafio.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMensagem {

    RECURSO_NAO_ENCONTRADO("recurso.not.found");

    private final String mensagem;
}
