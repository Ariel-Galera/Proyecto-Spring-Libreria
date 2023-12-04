package com.ariel.Proyectobiblioteca.servicios;

import com.ariel.Proyectobiblioteca.entidades.Autor;
import com.ariel.Proyectobiblioteca.entidades.Editorial;
import com.ariel.Proyectobiblioteca.entidades.Libro;
import com.ariel.Proyectobiblioteca.excepciones.MiException;
import com.ariel.Proyectobiblioteca.repositorios.AutorRepositorio;
import com.ariel.Proyectobiblioteca.repositorios.EditorialRepositorio;
import com.ariel.Proyectobiblioteca.repositorios.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
    public class LibroServicio {
       @Autowired
       private LibroRepositorio libroRepositorio;
       @Autowired
       private AutorRepositorio autorRepositorio;
       @Autowired
       private EditorialRepositorio editorialRepositorio;
       @Transactional
        public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

            validar(isbn, titulo, ejemplares, idAutor, idEditorial);


            Autor autor =autorRepositorio.findById(idAutor).get();
            Editorial editorial = editorialRepositorio.findById(idEditorial).get();

            Libro libro = new Libro();


            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAlta(new Date());

            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);


        }

        public List<Libro> listarLibros(){

            List<Libro> libros = new ArrayList<>();

            libros = libroRepositorio.findAll();

            return libros;
        }

        public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{

            validar(isbn, titulo, ejemplares, idAutor, idEditorial);

            Optional<Libro> respuesta = libroRepositorio.findById(isbn);
            Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
            Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

            Autor autor = new Autor();
            Editorial editorial = new Editorial();

            if(respuestaAutor.isPresent()){

                autor = respuestaAutor.get();
            }

            if (respuestaEditorial.isPresent()){

                editorial = respuestaEditorial.get();
            }


            if (respuesta.isPresent()){

                Libro libro = respuesta.get();

                libro.setTitulo(titulo);
                libro.setAutor(autor);
                libro.setEditorial(editorial);
                libro.setEjemplares(ejemplares);

                libroRepositorio.save(libro);
            }
        }

        private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        if (isbn == null) {
            throw new MiException("el isbn no puede ser nulo");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("el titulo no puede ser nulo o estar vacío");
        }

        if (ejemplares == null) {
            throw new MiException("ejemplares no puede ser nulo");
        }

        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiException("el id del autor no puede ser nulo o estar vacío");
        }

        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiException("el id del editorial no puede ser nulo o estar vacío");
        }
    }

    public Libro getOne(Long isbn){
        return libroRepositorio.getOne(isbn);
    }

}




