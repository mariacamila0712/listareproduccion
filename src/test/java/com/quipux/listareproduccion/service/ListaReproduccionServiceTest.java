package com.quipux.listareproduccion.service;

import com.quipux.listareproduccion.data.ListaReproduccionData;
import com.quipux.listareproduccion.entity.ListaReproduccion;
import com.quipux.listareproduccion.mapper.ListaMapper;
import com.quipux.listareproduccion.repository.ListaReproduccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListaReproduccionServiceTest {

    @InjectMocks
    private ListaReproduccionService service;

    @Mock
    private ListaReproduccionRepository repo;

    @Mock
    private ListaMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crear_deberiaGuardarYDevolverDTO() {
        ListaReproduccion input = new ListaReproduccion();
        input.setNombre("miLista");

        ListaReproduccion guardado = new ListaReproduccion();
        guardado.setNombre("miLista");

        ListaReproduccionData dto = new ListaReproduccionData();
        dto.setNombre("miLista");

        when(repo.save(input)).thenReturn(guardado);
        when(mapper.toDTO(guardado)).thenReturn(dto);

        ListaReproduccionData result = service.crear(input);

        assertEquals("miLista", result.getNombre());
        verify(repo).save(input);
        verify(mapper).toDTO(guardado);
    }

    @Test
    void crear_conNombreVacio_lanzaExcepcion() {
        ListaReproduccion input = new ListaReproduccion();
        input.setNombre("");

        assertThrows(IllegalArgumentException.class, () -> service.crear(input));
    }

    @Test
    void crear_conEspacioEnNombre_lanzaExcepcion() {
        ListaReproduccion input = new ListaReproduccion();
        input.setNombre("mi lista");

        assertThrows(IllegalArgumentException.class, () -> service.crear(input));
    }

    @Test
    void obtenerTodas_deberiaRetornarListaDeDTOs() {
        ListaReproduccion entidad = new ListaReproduccion();
        entidad.setNombre("rock");

        ListaReproduccionData dto = new ListaReproduccionData();
        dto.setNombre("rock");

        when(repo.findAll()).thenReturn(Collections.singletonList(entidad));
        when(mapper.toDTO(entidad)).thenReturn(dto);

        List<ListaReproduccionData> resultado = service.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("rock", resultado.get(0).getNombre());
    }

    @Test
    void obtenerPorNombre_existente_devuelveDTO() {
        ListaReproduccion entidad = new ListaReproduccion();
        entidad.setNombre("pop");

        ListaReproduccionData dto = new ListaReproduccionData();
        dto.setNombre("pop");

        when(repo.findByNombre("pop")).thenReturn(entidad);
        when(mapper.toDTO(entidad)).thenReturn(dto);

        ListaReproduccionData result = service.obtenerPorNombre("pop");

        assertEquals("pop", result.getNombre());
    }

    @Test
    void obtenerPorNombre_noExistente_lanzaNotFound() {
        when(repo.findByNombre("nada")).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> service.obtenerPorNombre("nada"));
    }

    @Test
    void eliminar_existente_eliminaCorrectamente() {
        ListaReproduccion entidad = new ListaReproduccion();
        when(repo.existsByNombre("rock")).thenReturn(true);
        when(repo.findByNombre("rock")).thenReturn(entidad);

        service.eliminar("rock");

        verify(repo).delete(entidad);
    }

    @Test
    void eliminar_noExistente_lanzaNotFound() {
        when(repo.existsByNombre("jazz")).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.eliminar("jazz"));
    }
}
