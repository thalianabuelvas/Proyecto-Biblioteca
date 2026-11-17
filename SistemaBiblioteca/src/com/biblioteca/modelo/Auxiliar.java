package com.biblioteca.modelo;

public class Auxiliar extends Personal {
    private String turno;

    public Auxiliar(String nombre, String id, String area, String turno) {
        super(nombre, id, area);
        this.turno = turno;
    }

    @Override
    public String generarReporte() {
        return "=== AUXILIAR ===\nNombre: " + getNombre() + "\nID: " + getId()
                + "\nÁrea: " + getArea() + "\nTurno: " + turno;
    }
}
