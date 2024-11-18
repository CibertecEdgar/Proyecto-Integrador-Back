package com.proyecto.integrador.backend.app.validation.password;
public class LowerCaseRule implements PasswordRule {
    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (password.chars().anyMatch(Character::isLowerCase)) {
        	System.out.println("lower");
            return true;
        }
        errorMessage.append("Debe contener al menos una letra minúscula. ");
        return false;
    }
}