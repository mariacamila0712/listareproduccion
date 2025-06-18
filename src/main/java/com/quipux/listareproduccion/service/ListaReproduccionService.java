package com.quipux.listareproduccion.service;

import com.quipux.listareproduccion.data.ListaReproduccionData;
import com.quipux.listareproduccion.entity.ListaReproduccion;
import com.quipux.listareproduccion.mapper.ListaMapper;
import com.quipux.listareproduccion.repository.ListaReproduccionRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaReproduccionService {
    @Autowired
    private ListaReproduccionRepository repo;

    @Autowired
    private ListaMapper mapper;

    public ListaReproduccionData crear(ListaReproduccion lista) {
        if (Strings.isEmpty(lista.getNombre())) throw new IllegalArgumentException("Nombre inválido");
        if (lista.getNombre().contains(" ")) throw new IllegalArgumentException("Nombre inválido");
        ListaReproduccion listaReproduccion = repo.save(lista);
        return mapper.toDTO(listaReproduccion);

    }

    public List<ListaReproduccionData> obtenerTodas() {
        return repo.findAll()
                .stream()
                .map(l -> mapper.toDTO(l))
                .collect(Collectors.toList());
    }

    public ListaReproduccionData obtenerPorNombre(String nombre) {
        ListaReproduccion listaReproduccion = repo.findByNombre(nombre);
        if(listaReproduccion!=null) {
            return mapper.toDTO(listaReproduccion);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void eliminar(String nombre) {
        if (!repo.existsByNombre(nombre)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        repo.delete(repo.findByNombre(nombre));
    }
}

