package com.biblioteca.modelo;

import java.util.ArrayList;

import com.biblioteca.excepciones.PersonalNoEncontradoException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;

/**
 * Composición con Seccion: las secciones se crean dentro de la biblioteca
 * y dejan de existir si la biblioteca desaparece.
 */
public class Biblioteca {
    private String nombre;
    private ArrayList<Seccion> secciones;
    private ArrayList<Personal> personal;

    public Biblioteca(String nombre) {
        this.nombre = nombre;
        this.secciones = new ArrayList<>();
        this.personal = new ArrayList<>();
        // Secciones iniciales (creadas internamente: composición)
        secciones.add(new Seccion("Referencia", 10));
        secciones.add(new Seccion("Literatura", 8));
        secciones.add(new Seccion("Ciencias", 6));
    }

    public void agregarSeccion(String nombre, int capacidad) {
        Seccion s = new Seccion(nombre, capacidad); // puede lanzar IllegalArgumentException
        secciones.add(s);
        System.out.println("Sección '" + nombre + "' creada con capacidad " + capacidad);
    }

    public void agregarPersonal(Personal p) {
        personal.add(p);
        System.out.println(p.getNombre() + " registrado en la biblioteca.");
    }

    public Seccion buscarSeccion(String nombre) {
        for (Seccion s : secciones) {
            if (s.getNombre().equalsIgnoreCase(nombre)) {
                return s;
            }
        }
        return null;
    }

    public Recurso buscarRecurso(String codigo) throws RecursoNoEncontradoException {
        for (Seccion s : secciones) {
            try {
                return s.buscarRecurso(codigo);
            } catch (RecursoNoEncontradoException e) {
                // no está en esta sección: seguimos con la siguiente
            }
        }
        throw new RecursoNoEncontradoException(codigo);
    }

    public Bibliotecario buscarBibliotecario(String id) throws PersonalNoEncontradoException {
        for (Personal p : personal) {
            if (p instanceof Bibliotecario && p.getId().equals(id)) {
                return (Bibliotecario) p;
            }
        }
        throw new PersonalNoEncontradoException(id);
    }

    public void listarBibliotecarios() {
        System.out.println("--- Bibliotecarios ---");
        for (Personal p : personal) {
            if (p instanceof Bibliotecario) {
                System.out.println(p.getId() + " - " + p.getNombre() + " (" + p.getArea() + ")");
            }
        }
    }

    public void generarReporteGeneral() {
        System.out.println("===== REPORTE GENERAL: " + nombre + " =====");
        for (Seccion s : secciones) {
            System.out.println("Sección: " + s.getNombre() + " | Ocupación: "
                    + s.getRecursos().size() + "/" + s.getCapacidad());
        }
        System.out.println("Total de personal: " + personal.size());
        for (Personal p : personal) {
            System.out.println(p.generarReporte()); // polimorfismo dinámico
            System.out.println();
        }
    }

    public ArrayList<Seccion> getSecciones() {
        return secciones;
    }

    public ArrayList<Personal> getPersonal() {
        return personal;
    }
}
