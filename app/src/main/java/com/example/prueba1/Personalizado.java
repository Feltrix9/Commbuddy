package com.example.prueba1;

public class Personalizado {
    private String nombre;
    private int imagenResId; // ID del recurso de la imagen
    private int sonidoResId; // ID del recurso de sonido

    public Personalizado(String nombre, int imagenResId, int sonidoResId) {
        this.nombre = nombre;
        this.imagenResId = imagenResId;
        this.sonidoResId = sonidoResId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public int getSonidoResId() {
        return sonidoResId;
    }
}
