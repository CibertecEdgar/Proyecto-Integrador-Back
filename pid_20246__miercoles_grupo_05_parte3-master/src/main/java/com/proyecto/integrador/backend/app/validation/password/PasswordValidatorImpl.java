package com.proyecto.integrador.backend.app.validation.password;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String> {

    private List<PasswordRule> rules;

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        rules = List.of(
            new LowerCaseRule(),
            new UpperCaseRule(),
            new DigitRule(),
            new SpecialCharacterRule(),
            new LengthRule(),
            new NoSpaceRule(),
            new RegexRule()
        );
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        StringBuilder errorMessage = new StringBuilder();
        boolean isValid = rules.stream()
                               .allMatch(rule -> rule.isValid(password, errorMessage));

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(errorMessage.toString())
                   .addConstraintViolation()
                   .disableDefaultConstraintViolation();
        }

        return isValid;
    }

}