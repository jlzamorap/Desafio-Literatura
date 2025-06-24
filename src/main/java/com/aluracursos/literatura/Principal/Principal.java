package com.aluracursos.literatura.Principal;

import com.aluracursos.literatura.Models.Datos;
import com.aluracursos.literatura.Repository.AutorRepository;
import com.aluracursos.literatura.Repository.LibroRepository;
import com.aluracursos.literatura.Service.ConsumoAPI;
import com.aluracursos.literatura.Service.IConvierteDatos;
import com.aluracursos.literatura.Service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private final Scanner teclado = new Scanner(System.in);

    @Autowired
    private LibroService libroService;

    @Autowired
    private ConsumoAPI consumoApi;

    @Autowired
    private IConvierteDatos conversor;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;


    public void muestraElMenu() {
        // Solo consumir la API si la base de datos está vacía
        if (libroService.estaVaciaLaBase()) {
            String json = consumoApi.obtenerDatos(URL_BASE);
            Datos datos = conversor.obtenerDatos(json, Datos.class);
            libroService.guardarLibrosDesdeAPI(datos.resultados());
            System.out.println("Libros cargados desde la API.");
        }

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Top 5 mejores libros
                    6 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosMismoPeriodo();
                    break;
                case 5:
                    buscarTop5Libros();
                    break;
                case 6:
                    buscarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }

    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro que desea buscar:");
        String titulo = teclado.nextLine();

        var libros = libroRepository.findByTituloContainingIgnoreCase(titulo);
        if (libros.isEmpty()) {
            System.out.println("-----No se encontraron libros con ese título.-----\n");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            System.out.println("-----------------------------");
        });
    }

    private void listarLibrosRegistrados () {
        var libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\n--- Libros registrados ---");
        libros.forEach(libro -> {
            System.out.println("-----Libro-----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());

            System.out.println("-----------------------------\n");
        });
    }

    private void listarAutoresRegistrados () {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        System.out.println("\n--- Autores registrados ---");
        autores.forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fecha de muerte: " + autor.getFechaMuerte());
            System.out.println("Cantidad de libros: " + autor.getLibros().size());
            System.out.println("-----------------------------");
        });
    }

    private void listarAutoresVivosMismoPeriodo () {
        System.out.println("Ingresa el año a consultar:");
        int anio = Integer.parseInt(teclado.nextLine());

        var autores = autorRepository.findAll().stream()
                .filter(a -> a.getFechaNacimiento() != null && a.getFechaNacimiento().getYear() <= anio)
                .filter(a -> a.getFechaMuerte() == null || a.getFechaMuerte().getYear() >= anio)
                .toList();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            return;
        }

        System.out.println("\nAutores vivos en " + anio + ":");
        autores.forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Muerte: " + autor.getFechaMuerte());
            System.out.println("-----------------------------");
        });
    }

    private void buscarTop5Libros () {
        System.out.println("Top 5 libros más descargados:");

        var libros = libroRepository.findTop5ByOrderByNumeroDeDescargasDesc();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        libros.forEach(libro ->
                System.out.println("- " + libro.getTitulo().toUpperCase() +
                        " (" + libro.getNumeroDeDescargas() + " descargas)")
        );
    }

    private void buscarLibrosPorIdioma () {
        System.out.println("Ingrese el idioma (por ejemplo, 'en', 'es'):");
        String idioma = teclado.nextLine();

        var libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            System.out.println("-----------------------------");
        });
    }

}
