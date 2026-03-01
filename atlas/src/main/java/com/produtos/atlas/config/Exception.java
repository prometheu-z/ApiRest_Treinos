package com.produtos.atlas.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class Exception extends Throwable {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validacao(MethodArgumentNotValidException e){
        var erros = e.getBindingResult().getFieldErrors().stream().map(error -> error.getField()+": "+error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }
}
