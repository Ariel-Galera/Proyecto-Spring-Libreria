package com.ariel.Proyectobiblioteca.servicios;

import com.ariel.Proyectobiblioteca.entidades.Autor;
import com.ariel.Proyectobiblioteca.excepciones.MiException;
import com.ariel.Proyectobiblioteca.repositorios.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;
    @Transactional
    public void crearAutor (String nombre) throws MiException{

        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores(){

        List <Autor> autores = new ArrayList();

        autores = autorRepositorio.findAll();

        return autores;
    }

    public void modificarAutor(String nombre, String id) throws MiException{

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()){
            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepositorio.save(autor);

        }
    }

    private void validar(String nombre) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre de autor no puede ser nulo o estar vacío");
        }

    }

   public Autor getOne(String id){
        return autorRepositorio.getOne(id);
   }




}
