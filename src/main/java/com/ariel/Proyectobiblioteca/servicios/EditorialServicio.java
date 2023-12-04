package com.ariel.Proyectobiblioteca.servicios;

import com.ariel.Proyectobiblioteca.entidades.Autor;
import com.ariel.Proyectobiblioteca.entidades.Editorial;
import com.ariel.Proyectobiblioteca.excepciones.MiException;
import com.ariel.Proyectobiblioteca.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;
    @Transactional
    public void crearEditorial(String nombre) throws MiException {

        validar(nombre);

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales(){

        List<Editorial> editoriales = new ArrayList();

        editoriales = editorialRepositorio.findAll();

        return editoriales;
    }

    public void modificarEditorial(String nombre, String id) throws MiException{

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if(respuesta.isPresent()){

            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }

    }

    private void validar(String nombre) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre de editorial no puede ser nulo o estar vacío");
        }

    }

    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }

}
