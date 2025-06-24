package com.aluracursos.literatura.Service;

import com.aluracursos.literatura.DTO.AutorDto;
import com.aluracursos.literatura.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<AutorDto> obtenerTodos() {
        return autorRepository.findAll().stream()
                .map(autor -> new AutorDto(autor.getNombre(), autor.getFechaNacimiento(),
                        autor.getFechaMuerte()))
                .toList();
    }

    public List<AutorDto> buscarAutoresVivosEnAnio(int anio) {
        return autorRepository.findAll().stream()
                .filter(a -> a.getFechaNacimiento() != null && a.getFechaNacimiento().getYear() <= anio)
                .filter(a -> a.getFechaMuerte() == null || a.getFechaMuerte().getYear() >= anio)
                .map(a -> new AutorDto(
                        a.getNombre(), a.getFechaNacimiento(), a.getFechaMuerte()))
                .toList();
    }
}
