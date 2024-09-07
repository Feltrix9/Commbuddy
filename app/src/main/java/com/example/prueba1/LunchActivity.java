package com.example.prueba1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LunchActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerDrinks, mediaPlayerMeat, mediaPlayerSoda, mediaPlayerFoots,
            mediaPlayerRice, mediaPlayerMash_Potatoes, mediaPlayerEgg, mediaPlayersoup, mediaPlayerWater, mediaPlayerApple,mediaPlayerLettuce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        // Inicializar MediaPlayer para cada botón
        mediaPlayerDrinks = MediaPlayer.create(this, R.raw.drinks_sound);
//        mediaPlayerMeat = MediaPlayer.create(this, R.raw.meat_sound);
//        mediaPlayerSoda = MediaPlayer.create(this, R.raw.soda_sound);
        mediaPlayerFoots = MediaPlayer.create(this, R.raw.foots_sound);
//        mediaPlayerRice = MediaPlayer.create(this, R.raw.rice_sound);
//        mediaPlayerMash_Potatoes = MediaPlayer.create(this, R.raw.mash_potatoes_sound);
//        mediaPlayerEgg = MediaPlayer.create(this, R.raw.egg_sound);
//        mediaPlayersoup = MediaPlayer.create(this, R.raw.soup_sound);
//        mediaPlayerWater = MediaPlayer.create(this, R.raw.water_sound);
//        mediaPlayerApple = MediaPlayer.create(this, R.raw.apple_sound);
//        mediaPlayerLettuce = MediaPlayer.create(this, R.raw.lettuce_sound);

        // Configurar listeners para cada botón
        findViewById(R.id.imageButtonDrinks).setOnClickListener(view -> playSound(mediaPlayerDrinks));
//        findViewById(R.id.imageButtonMeat).setOnClickListener(view -> playSound(mediaPlayerMeat));
//        findViewById(R.id.imageButtonSoda).setOnClickListener(view -> playSound(mediaPlayerSoda));
        findViewById(R.id.imageButtonFoot).setOnClickListener(view -> playSound(mediaPlayerFoots));
//        findViewById(R.id.imageButtonRice).setOnClickListener(view -> playSound(mediaPlayerRice));
//        findViewById(R.id.imageButtonMash_potatoes).setOnClickListener(view -> playSound(mediaPlayerMash_Potatoes));
//        findViewById(R.id.imageButtonEgg).setOnClickListener(view -> playSound(mediaPlayerEgg));
//        findViewById(R.id.imageButtonSoup).setOnClickListener(view -> playSound(mediaPlayersoup));
//        findViewById(R.id.imageButtonWater).setOnClickListener(view -> playSound(mediaPlayerWater));
//        findViewById(R.id.imageButtonApple).setOnClickListener(view -> playSound(mediaPlayerApple));
//        findViewById(R.id.imageButtonLettuce).setOnClickListener(view -> playSound(mediaPlayerLettuce));

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
//        releaseMediaPlayer(mediaPlayerMeat);
//        releaseMediaPlayer(mediaPlayerSoda);
        releaseMediaPlayer(mediaPlayerFoots);
//        releaseMediaPlayer(mediaPlayerRice);
//        releaseMediaPlayer(mediaPlayerMash_Potatoes);
//        releaseMediaPlayer(mediaPlayerEgg);
//        releaseMediaPlayer(mediaPlayersoup);
//        releaseMediaPlayer(mediaPlayerWater);
//        releaseMediaPlayer(mediaPlayerApple);
//        releaseMediaPlayer(mediaPlayerLettuce);

    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    }

