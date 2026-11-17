package com.biblioteca.excepciones;

public class PersonalNoEncontradoException extends Exception {
    public PersonalNoEncontradoException(String id) {
        super("No existe personal registrado con id " + id + ".");
    }
}
