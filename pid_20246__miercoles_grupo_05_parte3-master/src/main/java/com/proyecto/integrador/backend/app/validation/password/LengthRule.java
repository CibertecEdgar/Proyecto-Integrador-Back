package com.proyecto.integrador.backend.app.validation.password;
public class LengthRule implements PasswordRule {
    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (password.length() >= 6 && password.length() <= 30) {
        	System.out.println("lentgh");
            return true;
        }
        errorMessage.append("Debe tener una longitud entre 6 y 30 caracteres. ");
        return false;
    }
}