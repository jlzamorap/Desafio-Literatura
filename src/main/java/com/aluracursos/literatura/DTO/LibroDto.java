package com.aluracursos.literatura.DTO;

import com.aluracursos.literatura.Models.Autor;
import jakarta.persistence.ManyToOne;

public record LibroDto(

        String titulo,
        String idioma,
        Integer numeroDeDescargas
) {
}
