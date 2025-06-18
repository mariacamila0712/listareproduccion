package com.quipux.listareproduccion.controller;

import com.quipux.listareproduccion.data.ListaReproduccionData;
import com.quipux.listareproduccion.entity.ListaReproduccion;
import com.quipux.listareproduccion.service.ListaReproduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.List;

@RestController
public class ListaReproduccionController {

    @Autowired
    private ListaReproduccionService service;

    @PostMapping("/lists")
    public ResponseEntity<ListaReproduccionData> crearLista(@RequestBody ListaReproduccion lista) {
        ListaReproduccionData creada = null;
        try {
            creada = service.crear(lista);
        } catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.created(URI.create("/lists/" + lista.getNombre())).body(creada);
    }

    @GetMapping("/lists")
    public List<ListaReproduccionData> obtenerTodasListaReproduccion(){
        return service.obtenerTodas();
    }

    @GetMapping("/lists/{listName}")
    public ListaReproduccionData obtenerListaReproduccionPorNombre(@PathVariable("listName") String name){
        return service.obtenerPorNombre(name);
    }

    @DeleteMapping("/lists/{listName}")
    public ResponseEntity eliminar(@PathVariable("listName") String name){
         service.eliminar(name);
        return new ResponseEntity(HttpStatusCode.valueOf(204));
    }
}
