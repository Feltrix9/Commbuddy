package com.example.prueba1;

public class Personalizado {
    private String nombre;
    private String imagePath; // Ruta de la imagen en Firebase Storage
    private String audioPath; // Ruta del sonido en Firebase Storage

    public Personalizado(String nombre, String imagePath, String audioPath) {
        this.nombre = nombre;
        this.imagePath = imagePath;
        this.audioPath = audioPath;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagenPath() {
        return imagePath;
    }

    public String getAudioPath() {
        return audioPath;
    }
}
