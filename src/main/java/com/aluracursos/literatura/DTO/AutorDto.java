package com.aluracursos.literatura.DTO;

import com.aluracursos.literatura.Models.Libro;

import java.time.LocalDate;
import java.util.List;

public record AutorDto(

        String nombre,
        LocalDate fechaNacimiento,
        LocalDate fechaMuerte
) {
}
