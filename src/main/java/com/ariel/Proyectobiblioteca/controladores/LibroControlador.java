package com.ariel.Proyectobiblioteca.controladores;

import com.ariel.Proyectobiblioteca.entidades.Autor;
import com.ariel.Proyectobiblioteca.entidades.Editorial;
import com.ariel.Proyectobiblioteca.entidades.Libro;
import com.ariel.Proyectobiblioteca.excepciones.MiException;
import com.ariel.Proyectobiblioteca.servicios.AutorServicio;
import com.ariel.Proyectobiblioteca.servicios.EditorialServicio;
import com.ariel.Proyectobiblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/libro")

public class LibroControlador {
   @Autowired
   private LibroServicio libroServicio;
   @Autowired
    private AutorServicio autorServicio;
   @Autowired
    private EditorialServicio editorialServicio;

   @GetMapping("/registrar")//localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
       List<Autor> autores = autorServicio.listarAutores();
       List<Editorial> editoriales = editorialServicio.listarEditoriales();

       modelo.addAttribute("autores", autores);
       modelo.addAttribute("editoriales", editoriales);

       return "libro_form.html";
   }

   @PostMapping("/registro")
    public String registro (@RequestParam(required = false) Long isbn, @RequestParam String titulo,
                            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
                            @RequestParam String idEditorial, ModelMap modelo) {
       try {
           libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial); //Si todo sale bien retornamos al index
           modelo.put("exito","El libro fue cargado correctamente!" );

       } catch (MiException ex) {
           List<Autor> autores = autorServicio.listarAutores();
           List<Editorial> editoriales = editorialServicio.listarEditoriales();

           modelo.addAttribute("autores", autores);
           modelo.addAttribute("editoriales", editoriales);
           modelo.put("error", ex.getMessage());

           return "libro_form.html";//volvemos a cargar el formulario
       }
       return "index.html"; //Volvemos a cargar el formulario

    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
       List<Libro> libros = libroServicio.listarLibros();
       modelo.addAttribute("libros",libros);

       return "libro_list";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroServicio.getOne(isbn));

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {
        try {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            // Ensure the order of arguments matches the method definition
            libroServicio.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);

            return "redirect:../lista";
        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.put("error", ex.getMessage());

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            return "libro_modificar.html";
        }
    }
}
