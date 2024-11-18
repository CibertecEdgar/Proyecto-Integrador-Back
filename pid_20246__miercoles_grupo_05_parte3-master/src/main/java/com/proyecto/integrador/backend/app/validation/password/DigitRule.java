package com.proyecto.integrador.backend.app.validation.password;
public class DigitRule implements PasswordRule {
    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (password.chars().anyMatch(Character::isDigit)) {
        	System.out.println("digit");
            return true;
        }
        errorMessage.append("Debe contener al menos un dígito. ");
        return false;
    }
}