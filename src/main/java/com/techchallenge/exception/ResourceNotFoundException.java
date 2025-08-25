package com.techchallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Exceção personalizada para recursos não encontrados
@ResponseStatus(HttpStatus.NOT_FOUND)  // Retorna o status 404 Not Found
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}