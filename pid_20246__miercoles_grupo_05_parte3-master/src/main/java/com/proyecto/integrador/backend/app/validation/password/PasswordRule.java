package com.proyecto.integrador.backend.app.validation.password;
public interface PasswordRule {
    boolean isValid(String password, StringBuilder errorMessage);
}