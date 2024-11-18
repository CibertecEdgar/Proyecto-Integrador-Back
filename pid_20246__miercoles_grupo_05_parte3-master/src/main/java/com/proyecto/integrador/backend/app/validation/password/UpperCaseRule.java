package com.proyecto.integrador.backend.app.validation.password;
public class UpperCaseRule implements PasswordRule {
    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (password.chars().anyMatch(Character::isUpperCase)) {
        	System.out.println("upper");
            return true;
        }
        errorMessage.append("Debe contener al menos una letra mayúscula. ");
        return false;
    }
}