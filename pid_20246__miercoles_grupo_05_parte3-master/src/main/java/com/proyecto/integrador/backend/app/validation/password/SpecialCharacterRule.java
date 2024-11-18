package com.proyecto.integrador.backend.app.validation.password;
public class SpecialCharacterRule implements PasswordRule {

    private static final String SPECIALCHARACTERS = "!@#$%^&*()-=+[]{}|;:',.<>?/";  // Conjunto de caracteres especiales

    @Override
    public boolean isValid(String password, StringBuilder errorMessage) {
        // Verificar si la contraseña contiene al menos un carácter especial
        boolean containsSpecialCharacter = password.chars()
            .anyMatch(ch -> SPECIALCHARACTERS.indexOf(ch) >= 0);  // Busca cualquier carácter especial en la contraseña

        if (containsSpecialCharacter) {
            System.out.println("Contraseña contiene un carácter especial.");
            return true;  // Contraseña válida si tiene al menos un carácter especial
        }

        // Si no contiene caracteres especiales, agregar mensaje de error
        errorMessage.append("Debe contener al menos un carácter especial. ");
        return false;  // Contraseña no válida
    }
}