package com.maplink.desafio.exception.utils;

import com.maplink.desafio.exception.enums.ExceptionMensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MensagemUtils {

    @Autowired
    MessageSource messageSource;

    public String getMensagem(ExceptionMensagem mensagem) {
        return messageSource.getMessage(mensagem.getMensagem(), null, LocaleContextHolder.getLocale());
    }
}
