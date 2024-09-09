package com.example.prueba1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BreakfastActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerDrinks, mediaPlayerFoods;
    private ImageButton imageButtonSelectDrinksFirst, imageButtonSelectFoodsFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        // Inicializar MediaPlayer para cada botón
        mediaPlayerDrinks = MediaPlayer.create(this, R.raw.drinks_sound);
        mediaPlayerFoods = MediaPlayer.create(this, R.raw.foods_sound);

        // Inicializar botones
        imageButtonSelectDrinksFirst = findViewById(R.id.imageButtonDrinks);
        imageButtonSelectFoodsFirst = findViewById(R.id.imageButtonFoods);

        // Configurar listeners para cada opción
        imageButtonSelectFoodsFirst.setOnClickListener(view -> {
            Toast.makeText(BreakfastActivity.this, "Comida seleccionada", Toast.LENGTH_SHORT).show();
            playSound(mediaPlayerFoods);
            Intent intent = new Intent(BreakfastActivity.this, BreakfastFoodActivity.class);
            intent.putExtra("firstSelection", "food");
            startActivity(intent);
        });

        imageButtonSelectDrinksFirst.setOnClickListener(view -> {
            Toast.makeText(BreakfastActivity.this, "Bebida seleccionada", Toast.LENGTH_SHORT).show();
            playSound(mediaPlayerDrinks);
            Intent intent = new Intent(BreakfastActivity.this, BreakfastDrinkActivity.class);
            intent.putExtra("firstSelection", "drink");
            startActivity(intent);
        });

        // Botón para volver a la actividad anterior
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> finish());
    }

    private void playSound(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer(mediaPlayerDrinks);
        releaseMediaPlayer(mediaPlayerFoods);
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

