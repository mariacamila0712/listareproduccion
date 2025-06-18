package com.quipux.listareproduccion.mapper;

import com.quipux.listareproduccion.data.CancionesData;
import com.quipux.listareproduccion.data.ListaReproduccionData;
import com.quipux.listareproduccion.entity.Cancion;
import com.quipux.listareproduccion.entity.ListaReproduccion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListaMapper {

    public ListaReproduccionData
    toDTO(ListaReproduccion entity) {
        ListaReproduccionData dto = new ListaReproduccionData();
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());

        dto.setCanciones(entity.getCanciones().stream().map(c -> {
            CancionesData cDto = new CancionesData();
            cDto.setTitulo(c.getTitulo());
            cDto.setArtista(c.getArtista());
            cDto.setAnno(c.getAnno());
            cDto.setGenero(c.getGenero());
            return cDto;
        }).collect(Collectors.toList()));

        return dto;
    }

    public ListaReproduccion toEntity(ListaReproduccionData dto) {
        ListaReproduccion entity = new ListaReproduccion();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        List<Cancion> canciones = dto.getCanciones().stream().map(c -> {
            Cancion cancion = new Cancion();
            cancion.setTitulo(c.getTitulo());
            cancion.setArtista(c.getArtista());
            cancion.setAnno(c.getAnno());
            cancion.setGenero(c.getGenero());
            return cancion;
        }).collect(Collectors.toList());

        entity.setCanciones(canciones);
        return entity;
    }
}

