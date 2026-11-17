package com.biblioteca.modelo;

import java.util.ArrayList;

import com.biblioteca.excepciones.PrestamoInvalidoException;

public class Bibliotecario extends Personal {
    private ArrayList<Prestamo> prestamos;

    public Bibliotecario(String nombre, String id, String area) {
        super(nombre, id, area);
        this.prestamos = new ArrayList<>();
    }

    // Polimorfismo estático: SOBRECARGA (primera versión)
    public void registrarPrestamo(String fecha, String lector, Recurso recurso)
            throws PrestamoInvalidoException {
        validarPrestamo(fecha, lector, recurso);
        Prestamo p = new Prestamo(fecha, "Préstamo estándar", lector, recurso, this);
        prestamos.add(p);
        System.out.println("Préstamo registrado: " + recurso.getTitulo() + " a " + lector);
    }

    // Polimorfismo estático: SOBRECARGA (segunda versión, con observación)
    public void registrarPrestamo(String fecha, String lector, String observacion, Recurso recurso)
            throws PrestamoInvalidoException {
        validarPrestamo(fecha, lector, recurso);
        if (observacion == null || observacion.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La observación no puede estar vacía.");
        }
        Prestamo p = new Prestamo(fecha, observacion, lector, recurso, this);
        prestamos.add(p);
        System.out.println("Préstamo registrado: " + recurso.getTitulo() + " a " + lector
                + " (" + observacion + ")");
    }

    // Validaciones comunes a ambas versiones
    private void validarPrestamo(String fecha, String lector, Recurso recurso)
            throws PrestamoInvalidoException {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new PrestamoInvalidoException("La fecha no puede estar vacía.");
        }
        if (lector == null || lector.trim().isEmpty()) {
            throw new PrestamoInvalidoException("Debe indicarse el nombre del lector.");
        }
        if (recurso == null) {
            throw new PrestamoInvalidoException("Debe indicarse un recurso válido.");
        }
    }

    // Polimorfismo dinámico: SOBREESCRITURA
    @Override
    public String generarReporte() {
        return "=== BIBLIOTECARIO ===\nNombre: " + getNombre() + "\nID: " + getId()
                + "\nÁrea: " + getArea() + "\nPréstamos registrados: " + prestamos.size();
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
}
