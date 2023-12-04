package com.ariel.Proyectobiblioteca.controladores;

import com.ariel.Proyectobiblioteca.excepciones.MiException;
import com.ariel.Proyectobiblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

   @GetMapping("/")
    public String index(){

       return "index.html";
    }

    @GetMapping("/registrar")
       public String registrar(){
           return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
                           String password2, ModelMap modelo){

            try{
                usuarioServicio.registrar(nombre, email, password, password2);

                modelo.put("exito", "Usuario registrado correctamente!");
                return "index.html";

            }catch (MiException ex){
               modelo.put("error", ex.getMessage());
               modelo.put("nombre", nombre);
               modelo.put("email",email);

               return "registro.html";
            }

    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }


}
