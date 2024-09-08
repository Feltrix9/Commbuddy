package com.example.prueba1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LunchActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerDrinks, mediaPlayerMeat, mediaPlayerSoda, mediaPlayerFoods,
            mediaPlayerRice, mediaPlayerMash_Potatoes, mediaPlayerEgg, mediaPlayersoup, mediaPlayerWater, mediaPlayerApple,mediaPlayerLettuce;
    private ImageButton imageButtonDrinks, btnLunchFood, btnLunchDrink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        // Inicializar MediaPlayer para cada botón
        mediaPlayerDrinks = MediaPlayer.create(this, R.raw.drinks_sound);
        mediaPlayerFoods = MediaPlayer.create(this, R.raw.foods_sound);


        // Configurar listeners para cada botón
        findViewById(R.id.imageButtonDrinks).setOnClickListener(view -> playSound(mediaPlayerDrinks));
        findViewById(R.id.imageButtonFoods).setOnClickListener(view -> playSound(mediaPlayerFoods));



        // Inicializar botones
        btnLunchFood = findViewById(R.id.imageButtonFoods);
        btnLunchDrink = findViewById(R.id.imageButtonDrinks);



        // Configurar listeners para abrir nuevas actividades
        btnLunchFood.setOnClickListener(view -> {
            Toast.makeText(LunchActivity.this, "Comida seleccionado", Toast.LENGTH_SHORT).show();
            playSound(mediaPlayerFoods);
            Intent intent = new Intent(LunchActivity.this, LunchFoodActivity.class);
            startActivity(intent);
        });

        // Configurar listeners para abrir nuevas actividades
        btnLunchDrink.setOnClickListener(view -> {
            Toast.makeText(LunchActivity.this, "Bebestible seleccionado", Toast.LENGTH_SHORT).show();
            playSound(mediaPlayerDrinks);
            Intent intent = new Intent(LunchActivity.this, LunchDrinkActivity.class);
            startActivity(intent);
        });




        // Botón para volver a la actividad anterior
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar la actividad actual y volver a la anterior
                finish();
            }
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