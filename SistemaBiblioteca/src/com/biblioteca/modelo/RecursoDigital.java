package com.biblioteca.modelo;

public final class RecursoDigital extends Recurso {
    private String formato;
    private int tamanoMB;

    public RecursoDigital(String titulo, String codigo, int anio, String formato, int tamanoMB) {
        super(titulo, codigo, anio);
        if (tamanoMB <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor a 0 MB.");
        }
        this.formato = formato;
        this.tamanoMB = tamanoMB;
    }

    @Override
    public String obtenerInfo() {
        return "Recurso digital: " + getTitulo() + " | Código: " + getCodigo() + " | Año: " + getAnio()
                + " | Formato: " + formato + " | Tamaño: " + tamanoMB + " MB";
    }

    public int getTamanoMB() {
        return tamanoMB;
    }
}
