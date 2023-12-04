package com.ariel.Proyectobiblioteca.controladores;

import com.ariel.Proyectobiblioteca.entidades.Editorial;
import com.ariel.Proyectobiblioteca.excepciones.MiException;
import com.ariel.Proyectobiblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping ("/editorial")  //localhost:8080/editorial //localhost:8080/editorial
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;
    @GetMapping("/registrar") // //localhost:8080/editorial/registrar
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La Editorial fue registrada correctamente");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return"editorial_form.html";

        }
        return"index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){

        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("editoriales", editoriales);

        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialServicio.getOne(id));

        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(nombre, id);

            return "redirect:../lista";

        }catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }

    }



}
