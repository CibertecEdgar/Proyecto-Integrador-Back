package com.proyecto.integrador.backend.app.validation.password;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PasswordValidatorImpl.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {

    String message() default "La contraseña no cumple con los requisitos.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}