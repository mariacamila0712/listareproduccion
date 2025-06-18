package com.quipux.listareproduccion.repository;

import com.quipux.listareproduccion.entity.ListaReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, Long>{

    ListaReproduccion findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
