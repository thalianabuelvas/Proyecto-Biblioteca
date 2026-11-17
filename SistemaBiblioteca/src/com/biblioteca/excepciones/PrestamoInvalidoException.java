package com.biblioteca.excepciones;

public class PrestamoInvalidoException extends Exception {
    public PrestamoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
