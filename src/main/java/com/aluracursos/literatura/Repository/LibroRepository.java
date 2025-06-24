package com.aluracursos.literatura.Repository;

import com.aluracursos.literatura.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);

    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findTop5ByOrderByNumeroDeDescargasDesc();

    List<Libro> findByIdioma(String idioma);


}
