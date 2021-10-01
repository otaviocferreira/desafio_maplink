package com.maplink.desafio.exception.handler;

import com.maplink.desafio.exception.BusinessException;
import com.maplink.desafio.exception.builder.ExceptionResponseBuilder;
import com.maplink.desafio.exception.response.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ExceptionResponseBuilder exceptionResponseBuilder;

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException exception) {
        ExceptionResponse response = exceptionResponseBuilder.buildExceptionResponse(exception, exception.getMensagem());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
