package com.example.prueba1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class BreakfastActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerDrinks, mediaPlayerTea, mediaPlayerMilk, mediaPlayerYogurt,
            mediaPlayerFoots, mediaPlayerMuffin, mediaPlayerEgg, mediaPlayerApple, mediaPlayerWater;
    private TextView textView;
    private ImageButton imageButtonDrinks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        // Inicializar MediaPlayer para cada botón
        mediaPlayerDrinks = MediaPlayer.create(this, R.raw.drinks_sound);
//        mediaPlayerTea = MediaPlayer.create(this, R.raw.tea_sound);
//        mediaPlayerMilk = MediaPlayer.create(this, R.raw.milk_sound);
//        mediaPlayerYogurt = MediaPlayer.create(this, R.raw.yogurt_sound);
        mediaPlayerFoots = MediaPlayer.create(this, R.raw.foots_sound);
//        mediaPlayerMuffin = MediaPlayer.create(this, R.raw.muffin_sound);
//        mediaPlayerEgg = MediaPlayer.create(this, R.raw.egg_sound);
//        mediaPlayerApple = MediaPlayer.create(this, R.raw.apple_sound);
//        mediaPlayerWater = MediaPlayer.create(this, R.raw.water_sound);

         // Configurar listeners para cada botón
        findViewById(R.id.imageButtonDrinks).setOnClickListener(view -> playSound(mediaPlayerDrinks));
//        findViewById(R.id.imageButtonTea).setOnClickListener(view -> playSound(mediaPlayerTea));
//        findViewById(R.id.imageButtonMilk).setOnClickListener(view -> playSound(mediaPlayerMilk));
//        findViewById(R.id.imageButtonYogurt).setOnClickListener(view -> playSound(mediaPlayerYogurt));
        findViewById(R.id.imageButtonFoot).setOnClickListener(view -> playSound(mediaPlayerFoots));
//        findViewById(R.id.imageButtonMuffin).setOnClickListener(view -> playSound(mediaPlayerMuffin));
//        findViewById(R.id.imageButtonEgg).setOnClickListener(view -> playSound(mediaPlayerEgg));
//        findViewById(R.id.imageButtonApple).setOnClickListener(view -> playSound(mediaPlayerApple));
//        findViewById(R.id.imageButtonWater).setOnClickListener(view -> playSound(mediaPlayerWater));


//        textView = findViewById(R.id.textViewChat);
//        imageButtonJuice = findViewById(R.id.imageButtonJuice);

//        // Configurar la acción del ImageButton para cambiar el texto
//        imageButtonDrinks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.setText("Jugo Rico");
//            }
//        });

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
//        releaseMediaPlayer(mediaPlayerTea);
//        releaseMediaPlayer(mediaPlayerMilk);
//        releaseMediaPlayer(mediaPlayerYogurt);
        releaseMediaPlayer(mediaPlayerFoots);
//        releaseMediaPlayer(mediaPlayerMuffin);
//        releaseMediaPlayer(mediaPlayerEgg);
//        releaseMediaPlayer(mediaPlayerApple);
//        releaseMediaPlayer(mediaPlayerWater);
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }






    }

