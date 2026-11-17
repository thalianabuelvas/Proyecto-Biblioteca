# Sistema de Gestión de Biblioteca Universitaria

Proyecto final de Programación Orientada a Objetos (Java, menú por consola).

## Cómo ejecutarlo en VS Code

1. Instala un **JDK 17 o superior** y la extensión **Extension Pack for Java** de VS Code.
2. Abre la carpeta `SistemaBiblioteca` (File → Open Folder).
3. Abre `src/com/biblioteca/app/App.java` y pulsa **Run** sobre `main` (o F5).

Desde terminal, dentro de la carpeta del proyecto:

```bash
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -Dfile.encoding=UTF-8 -cp bin com.biblioteca.app.App
```

(En Windows PowerShell: `javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse src -Filter *.java).FullName`)

## Estructura

```
src/com/biblioteca/
├── modelo/        Personal (abstracta), Bibliotecario, Auxiliar,
│                  Recurso (abstracta), Libro, RecursoDigital (final),
│                  Prestamo, Seccion, Biblioteca, Validador
├── excepciones/   SeccionLlenaException, RecursoNoEncontradoException,
│                  PersonalNoEncontradoException, PrestamoInvalidoException
└── app/           App (menú principal)
docs/diagrama-biblioteca.puml   Diagrama de clases (PlantUML)
```

## Conceptos de POO aplicados

| Concepto | Dónde |
|---|---|
| Herencia + clases abstractas | `Personal` → `Bibliotecario`, `Auxiliar`; `Recurso` → `Libro`, `RecursoDigital` |
| Clase `final` | `RecursoDigital` |
| Polimorfismo estático (sobrecarga) | `Bibliotecario.registrarPrestamo(...)` (2 versiones) |
| Polimorfismo dinámico (sobreescritura) | `generarReporte()`, `obtenerInfo()` |
| Composición | `Biblioteca` crea y es dueña de sus `Seccion` |
| Agregación | `Seccion` guarda `ArrayList<Recurso>` (los recursos existen sin sección) |
| Asociación | `Bibliotecario` → `Prestamo` → `Recurso` |
| Encapsulamiento | Todos los atributos `private` |
| Excepciones personalizadas | Paquete `excepciones` |

## Decisión de diseño documentada

`Biblioteca.buscarRecurso(codigo)` **solo encuentra recursos que ya fueron ubicados en una sección**.
Por eso, para registrar un préstamo (opción 4) o buscar un recurso (opción 8), primero hay que
registrar el recurso (opción 1) y **ubicarlo en una sección (opción 3)**. Así el préstamo siempre
queda asociado a un recurso real que está físicamente en la biblioteca, nunca a `null`.

## Diagrama de clases

Abre `docs/diagrama-biblioteca.puml` con la extensión **PlantUML** (jebbs) y pulsa `Alt+D`,
o pega el contenido en <https://www.plantuml.com/plantuml/uml/> y descarga el SVG/PNG.
Luego inclúyelo en tu informe con: `![Diagrama de clases](diagrama-biblioteca.svg)`
