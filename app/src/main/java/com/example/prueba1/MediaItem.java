package com.example.prueba1;

public class MediaItem {
    private String audioPath;
    private String imagePath;
    private String name;

    public MediaItem(String audioPath, String imagePath, String name) {
        this.audioPath = audioPath;
        this.imagePath = imagePath;
        this.name = name;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }
}

