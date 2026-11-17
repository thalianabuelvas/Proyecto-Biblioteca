package com.biblioteca.excepciones;

public class RecursoNoEncontradoException extends Exception {
    public RecursoNoEncontradoException(String codigo) {
        super("Recurso con código " + codigo + " no encontrado.");
    }
}
