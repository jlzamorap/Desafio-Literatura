package com.aluracursos.literatura.Service;

import com.aluracursos.literatura.DTO.LibroDto;
import com.aluracursos.literatura.Models.Autor;
import com.aluracursos.literatura.Models.DatosAutor;
import com.aluracursos.literatura.Models.DatosLibros;
import com.aluracursos.literatura.Models.Libro;
import com.aluracursos.literatura.Repository.AutorRepository;
import com.aluracursos.literatura.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void guardarLibrosDesdeAPI(List<DatosLibros> datosLibros) {
        List<Libro> libros = datosLibros.stream()
                .map(datos -> {
                    if (datos.autor() == null || datos.autor().isEmpty()) {
                        return null;
                    }

                    if (libroRepository.existsByTitulo(datos.titulo())) {
                        return null; // Libro ya guardado
                    }

                    DatosAutor datosAutor = datos.autor().get(0);

                    // Buscar autor existente o crear nuevo
                    Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                            .orElseGet(() -> {
                                Autor nuevoAutor = new Autor();
                                nuevoAutor.setNombre(datosAutor.nombre());

                                if (datosAutor.fechaNacimiento() != null) {
                                    nuevoAutor.setFechaNacimiento(LocalDate.of(datosAutor.fechaNacimiento(), 1, 1));
                                }

                                if (datosAutor.fechaMuerte() != null) {
                                    nuevoAutor.setFechaMuerte(LocalDate.of(datosAutor.fechaMuerte(), 1, 1));
                                }

                                return autorRepository.save(nuevoAutor);
                            });

                    Libro libro = new Libro();
                    libro.setTitulo(datos.titulo());
                    libro.setIdioma(datos.idiomas().isEmpty() ? "N/A" : datos.idiomas().get(0));
                    libro.setNumeroDeDescargas(datos.numeroDeDescargas().intValue());
                    libro.setAutor(autor);

                    return libro;
                })
                .filter(Objects::nonNull)
                .toList();

        libroRepository.saveAll(libros);
    }

    public boolean estaVaciaLaBase() {
        return libroRepository.count() == 0;
    }

    public List<LibroDto> obtenerTodos() {
        return libroRepository.findAll().stream()
                .map(libro -> new LibroDto(libro.getTitulo(), libro.getIdioma(),
                        libro.getNumeroDeDescargas()))
                .toList();
    }

    public List<LibroDto> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(libro -> new LibroDto(libro.getTitulo(), libro.getIdioma(),
                        libro.getNumeroDeDescargas()))
                .toList();
    }

    public List<LibroDto> buscarPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma).stream()
                .map(libro -> new LibroDto(libro.getTitulo(), libro.getIdioma(),
                        libro.getNumeroDeDescargas()))
                .toList();
    }

    public List<LibroDto> obtenerTop5() {
        return libroRepository.findTop5ByOrderByNumeroDeDescargasDesc().stream()
                .map(libro -> new LibroDto(libro.getTitulo(), libro.getIdioma(),
                        libro.getNumeroDeDescargas()))
                .toList();
    }
}
