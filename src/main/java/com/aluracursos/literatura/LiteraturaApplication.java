package com.aluracursos.literatura;

import com.aluracursos.literatura.Principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Autowired
	private Principal principal;

	@Override
	public void run(String... args) throws Exception {

		principal.muestraElMenu();

	}

}

