package com.proyecto.integrador.backend.app.validation.password;
public class NoSpaceRule implements PasswordRule {
    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (!password.contains(" ")) {
        	System.out.println("no space");
            return true;
        }
        errorMessage.append("No debe contener espacios en blanco. ");
        return false;
    }
}