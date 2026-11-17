package com.biblioteca.app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.biblioteca.excepciones.PersonalNoEncontradoException;
import com.biblioteca.excepciones.PrestamoInvalidoException;
import com.biblioteca.excepciones.RecursoNoEncontradoException;
import com.biblioteca.excepciones.SeccionLlenaException;
import com.biblioteca.modelo.Auxiliar;
import com.biblioteca.modelo.Biblioteca;
import com.biblioteca.modelo.Bibliotecario;
import com.biblioteca.modelo.Libro;
import com.biblioteca.modelo.Prestamo;
import com.biblioteca.modelo.Recurso;
import com.biblioteca.modelo.RecursoDigital;
import com.biblioteca.modelo.Seccion;

public class App {

    /** Insiste hasta recibir un entero válido. */
    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine(); // limpia el buffer
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero.");
                sc.nextLine(); // descarta la entrada inválida
            }
        }
    }

    private static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static Recurso buscarRegistrado(ArrayList<Recurso> lista, String codigo) {
        for (Recurso r : lista) {
            if (r.getCodigo().equals(codigo)) {
                return r;
            }
        }
        return null;
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ BIBLIOTECA =====");
        System.out.println("1.  Registrar recurso");
        System.out.println("2.  Listar recursos registrados");
        System.out.println("3.  Ubicar recurso en una sección");
        System.out.println("4.  Registrar préstamo");
        System.out.println("5.  Ver recursos de una sección");
        System.out.println("6.  Ver préstamos de un bibliotecario");
        System.out.println("7.  Dar de baja un recurso de una sección");
        System.out.println("8.  Buscar recurso por código");
        System.out.println("9.  Reporte general de la biblioteca");
        System.out.println("10. Crear nueva sección");
        System.out.println("0.  Salir");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Recurso> recursosRegistrados = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca("Biblioteca Central Los Libertadores");

        Bibliotecario b1 = new Bibliotecario("Marta Rojas", "B001", "Circulación");
        Bibliotecario b2 = new Bibliotecario("Julián Pardo", "B002", "Referencia");
        Auxiliar a1 = new Auxiliar("Diana Cruz", "A001", "Catalogación", "Tarde");
        biblioteca.agregarPersonal(b1);
        biblioteca.agregarPersonal(b2);
        biblioteca.agregarPersonal(a1);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Opción: ");

            switch (opcion) {
                case 1: { // Registrar recurso
                    int tipo = leerEntero(sc, "Tipo (1=Libro, 2=Digital): ");
                    if (tipo != 1 && tipo != 2) {
                        System.out.println("Tipo inválido.");
                        break;
                    }
                    String titulo = leerTexto(sc, "Título: ");
                    String codigo = leerTexto(sc, "Código: ");
                    int anio = leerEntero(sc, "Año: ");
                    try {
                        Recurso nuevo;
                        if (tipo == 1) {
                            String autor = leerTexto(sc, "Autor: ");
                            int paginas = leerEntero(sc, "Páginas: ");
                            nuevo = new Libro(titulo, codigo, anio, autor, paginas);
                        } else {
                            String formato = leerTexto(sc, "Formato (PDF, EPUB...): ");
                            int mb = leerEntero(sc, "Tamaño en MB: ");
                            nuevo = new RecursoDigital(titulo, codigo, anio, formato, mb);
                        }
                        if (buscarRegistrado(recursosRegistrados, codigo) != null) {
                            System.out.println("Error: ya existe un recurso registrado con ese código.");
                        } else {
                            recursosRegistrados.add(nuevo);
                            System.out.println("Recurso registrado: " + nuevo.getTitulo());
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 2: { // Listar recursos registrados
                    if (recursosRegistrados.isEmpty()) {
                        System.out.println("No hay recursos registrados.");
                    } else {
                        for (Recurso r : recursosRegistrados) {
                            System.out.println(r.obtenerInfo());
                        }
                    }
                    break;
                }
                case 3: { // Ubicar recurso en una sección
                    String nombreSeccion = leerTexto(sc, "Nombre de la sección: ");
                    Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
                    if (seccion == null) {
                        System.out.println("Error: la sección '" + nombreSeccion + "' no existe.");
                        break;
                    }
                    String codigo = leerTexto(sc, "Código del recurso: ");
                    Recurso r = buscarRegistrado(recursosRegistrados, codigo);
                    if (r == null) {
                        System.out.println("Error: el recurso no está registrado.");
                        break;
                    }
                    try {
                        seccion.agregarRecurso(r);
                    } catch (SeccionLlenaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 4: { // Registrar préstamo
                    biblioteca.listarBibliotecarios();
                    String id = leerTexto(sc, "ID del bibliotecario: ");
                    try {
                        Bibliotecario bib = biblioteca.buscarBibliotecario(id);
                        String codigo = leerTexto(sc, "Código del recurso: ");
                        Recurso recurso = biblioteca.buscarRecurso(codigo);
                        String fecha = leerTexto(sc, "Fecha (dd/mm/aaaa): ");
                        String lector = leerTexto(sc, "Nombre del lector: ");
                        String resp = leerTexto(sc, "¿Agregar observación? (s/n): ");
                        if (resp.trim().equalsIgnoreCase("s")) {
                            String obs = leerTexto(sc, "Observación: ");
                            bib.registrarPrestamo(fecha, lector, obs, recurso);
                        } else {
                            bib.registrarPrestamo(fecha, lector, recurso);
                        }
                    } catch (PersonalNoEncontradoException | RecursoNoEncontradoException
                             | PrestamoInvalidoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 5: { // Ver recursos de una sección
                    String nombreSeccion = leerTexto(sc, "Nombre de la sección: ");
                    Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
                    if (seccion == null) {
                        System.out.println("Error: la sección '" + nombreSeccion + "' no existe.");
                    } else {
                        seccion.listarRecursos();
                    }
                    break;
                }
                case 6: { // Ver préstamos de un bibliotecario
                    String id = leerTexto(sc, "ID del bibliotecario: ");
                    try { // ejecuta el codigo hasta el break y vigila los errores.
                        Bibliotecario bib = biblioteca.buscarBibliotecario(id);
                        if (bib.getPrestamos().isEmpty()) {
                            System.out.println("Este bibliotecario no tiene préstamos.");
                        } else {
                            for (Prestamo p : bib.getPrestamos()) {
                                System.out.println(p.getInfo());
                            }
                        }
                    } catch (PersonalNoEncontradoException e) { //captura y maneja erroressss
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 7: { // Dar de baja un recurso
                    String nombreSeccion = leerTexto(sc, "Nombre de la sección: ");
                    Seccion seccion = biblioteca.buscarSeccion(nombreSeccion);
                    if (seccion == null) {
                        System.out.println("Error: la sección '" + nombreSeccion + "' no existe.");
                        break;
                    }
                    String codigo = leerTexto(sc, "Código del recurso a dar de baja: ");
                    try {
                        seccion.retirarRecurso(codigo);
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 8: { // Buscar recurso por código
                    String codigo = leerTexto(sc, "Código a buscar: ");
                    try {
                        Recurso r = biblioteca.buscarRecurso(codigo);
                        System.out.println(r.obtenerInfo());
                    } catch (RecursoNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 9:
                    biblioteca.generarReporteGeneral();
                    break;
                case 10: { // Crear nueva sección
                    String nombre = leerTexto(sc, "Nombre de la nueva sección: ");
                    int capacidad = leerEntero(sc, "Capacidad: ");
                    try {
                        biblioteca.agregarSeccion(nombre, capacidad);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case 0:
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        sc.close();
    }
}
