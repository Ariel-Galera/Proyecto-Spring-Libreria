package com.ariel.Proyectobiblioteca.repositorios;

import com.ariel.Proyectobiblioteca.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

}


