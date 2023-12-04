package com.ariel.Proyectobiblioteca.repositorios;

import com.ariel.Proyectobiblioteca.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>  {


}
