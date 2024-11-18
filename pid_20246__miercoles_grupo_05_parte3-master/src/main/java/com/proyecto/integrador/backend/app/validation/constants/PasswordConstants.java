package com.proyecto.integrador.backend.app.validation.constants;
public final class PasswordConstants {

    private PasswordConstants() {
    }

    // Expresión regular para contraseña
    public static final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%?&])[A-Za-z\\d$@$!%?&]{8,15}$";
}