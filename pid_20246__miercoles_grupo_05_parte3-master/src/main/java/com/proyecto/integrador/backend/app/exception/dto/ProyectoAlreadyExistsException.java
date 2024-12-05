package com.proyecto.integrador.backend.app.exception.dto;
public class ProyectoAlreadyExistsException extends RuntimeException {
    public ProyectoAlreadyExistsException(String message) {
        super(message);
    }
}