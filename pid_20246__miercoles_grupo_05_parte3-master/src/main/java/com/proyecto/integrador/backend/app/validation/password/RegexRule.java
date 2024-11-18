package com.proyecto.integrador.backend.app.validation.password;

import com.proyecto.integrador.backend.app.validation.constants.PasswordConstants;

public class RegexRule implements PasswordRule {

    private final String regex;

    public RegexRule() {
        this.regex = PasswordConstants.REGEX;
    }

    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        if (password.matches(regex)) {
        	System.out.println("regex");
            return true;
        }
        errorMessage.append("no cumple con los requisitos de la expresión regular. ");
        return false;
    }


}