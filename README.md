
# Desafio literatura


El presente desafio consiste en construir un catálogo de libros en el cual se realizara una solicitud a una API de libros, luego se manipularan los datos JSON, para guardarlos en una base de datos y, finalmente, se filtraran y mostraran los libros y autores de interés.
Lo anterior se realizara mediante una interaccion textual (via consola) con los usuarios en donde se proporcionan 6 opciones de interaccion.

## Caracteristicas y funcionalidades principales


**Funcionalidades principales**

El método muestraElMenu() es el punto de entrada de la interacción con el usuario. A continuación, se detalla su funcionamiento.

- Carga inicial de datos: Al iniciar la aplicación, verifica si la base de datos está vacía (libroService.estaVaciaLaBase()). Si lo está, consume la API de Gutendex (URL_BASE), convierte los datos a objetos Datos y los guarda en la base de datos a través de libroService.guardarLibrosDesdeAPI(). Esto asegura que la aplicación tenga datos con los que trabajar desde el principio.

- Menú interactivo: Presenta un menú de opciones al usuario y lo mantiene en un bucle hasta que el usuario decide salir (opción 0). Las opciones disponibles son:
1 - *Buscar libro por título (buscarLibroPorTitulo()):* Solicita al usuario un título y busca libros que contengan ese texto (ignorando mayúsculas y minúsculas) en la base de datos. Muestra el título, autor, idioma y número de descargas de los libros encontrados.

2 - *Listar libros registrados (listarLibrosRegistrados()):* Recupera y muestra todos los libros almacenados en la base de datos, incluyendo su título, autor, idioma y número de descargas.

3 - *Listar autores registrados (listarAutoresRegistrados()):* Muestra todos los autores en la base de datos, incluyendo su nombre, fecha de nacimiento, fecha de muerte y la cantidad de libros asociados a ellos.

4 - *Listar autores vivos en determinado año (listarAutoresVivosMismoPeriodo()):* Pide un año al usuario y lista los autores que estaban vivos en ese período (es decir, su fecha de nacimiento es anterior o igual al año ingresado y su fecha de muerte es posterior o igual al año ingresado, o aún no han fallecido).

5 - *Top 5 mejores libros (buscarTop5Libros()):* Muestra los 5 libros con mayor número de descargas, ordenados de forma descendente.

6 - *Listar libros por idioma (buscarLibrosPorIdioma()):* Permite al usuario ingresar un código de idioma (por ejemplo, 'en' para inglés, 'es' para español) y lista todos los libros disponibles en ese idioma.


## API Reference


https://gutendex.com/books/

## Tech Stack

**Back-kend** Java, Spring boot

**Servidor:** Localhost

**Base de Datos** PostgreSQL
