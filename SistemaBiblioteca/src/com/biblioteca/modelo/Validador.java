package com.biblioteca.modelo;

/**
 * Clase de utilidad con validaciones reutilizables.
 * No se puede instanciar (constructor privado).
 */
public class Validador {

    private Validador() {
    }

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isDigit(nombre.charAt(i))) {
                throw new IllegalArgumentException("El nombre no puede contener números: " + nombre);
            }
        }
    }

    public static void validarCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty() || codigo.contains(" ")) {
            throw new IllegalArgumentException("Código inválido: no puede ser vacío ni tener espacios.");
        }
    }
}
