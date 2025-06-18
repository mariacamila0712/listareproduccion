package com.quipux.listareproduccion.data;

import java.util.List;

public class ListaReproduccionData {

    private String nombre;
    private String descripcion;
    private List<CancionesData> canciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<CancionesData> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionesData> canciones) {
        this.canciones = canciones;
    }
}
