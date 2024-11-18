package com.proyecto.integrador.backend.app.exception.dto;
public class ErrorResponseLogin {

    private String message;

    public ErrorResponseLogin(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}