package com.example.prueba1;

import android.os.Bundle;


import android.media.MediaPlayer;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;





public class MainActivity extends AppCompatActivity {

    private ImageButton btnBreakfast, btnLunch, btnDinner;
    private MediaPlayer mediaPlayerBreakfast, mediaPlayerLunch, mediaPlayerDinner;
    private Button btnGoToCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el MediaPlayer con el sonido
        mediaPlayerBreakfast = MediaPlayer.create(this, R.raw.breakfast_sound);
        mediaPlayerLunch = MediaPlayer.create(this, R.raw.lunch_sound);
        mediaPlayerDinner = MediaPlayer.create(this, R.raw.dinner_sound);

        btnGoToCrud = findViewById(R.id.btnGoToCrud);


        // Verificar si los archivos de sonido existen
        if (mediaPlayerLunch == null) {
            Toast.makeText(this, "Error: Archivo de sonido para almuerzo no encontrado", Toast.LENGTH_SHORT).show();
        }

        if (mediaPlayerDinner == null) {
            Toast.makeText(this, "Error: Archivo de sonido para cena no encontrado", Toast.LENGTH_SHORT).show();
        }

        // Inicializar botones
        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);

        // Configurar listeners para abrir nuevas actividades
        btnBreakfast.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Desayuno seleccionado", Toast.LENGTH_SHORT).show();
            playSound(mediaPlayerBreakfast);
            Intent intent = new Intent(MainActivity.this, BreakfastActivity.class);
            startActivity(intent);
        });

        btnLunch.setOnClickListener(view -> {
            if (mediaPlayerLunch != null) {
                playSound(mediaPlayerLunch);
                Toast.makeText(MainActivity.this, "Almuerzo seleccionado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LunchActivity.class);
                startActivity(intent);
            }
        });

        btnDinner.setOnClickListener(view -> {
            if (mediaPlayerDinner != null) {
                playSound(mediaPlayerDinner);
                Toast.makeText(MainActivity.this, "Cena seleccionada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DinnerActivity.class);
                startActivity(intent);
            }
        });

        btnGoToCrud.setOnClickListener(view -> {
            // Navegar a la actividad CRUD
            Intent intent = new Intent(MainActivity.this, CrudActivity.class);
            startActivity(intent);
        });

    }

    private void playSound(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer(mediaPlayerBreakfast);
        releaseMediaPlayer(mediaPlayerLunch);
        releaseMediaPlayer(mediaPlayerDinner);
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

