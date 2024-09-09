package com.example.prueba1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DinnerDrinkActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerJuice, mediaPlayerTea, mediaPlayerMilk, mediaPlayerYogurt, mediaPlayerWater;
    private TextView textView;
    private ImageButton imageButtonJuice, imageButtonTea, imageButtonWater, imageButtonMilk, imagenButtonYogurt;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinnerdrinks);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("es", "MX"));
            }
        });

        // Inicializar MediaPlayer para cada botón
        mediaPlayerJuice = MediaPlayer.create(this, R.raw.juice_sound);
        mediaPlayerTea = MediaPlayer.create(this, R.raw.tea_sound);
        mediaPlayerMilk = MediaPlayer.create(this, R.raw.milk_sound);
        mediaPlayerYogurt = MediaPlayer.create(this, R.raw.yogurt_sound);
        mediaPlayerWater = MediaPlayer.create(this, R.raw.water_sound);


        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonJuice = findViewById(R.id.imageButtonJuice);
        imageButtonTea = findViewById(R.id.imageButtonTea);
        imageButtonMilk = findViewById(R.id.imageButtonMilk);
        imagenButtonYogurt = findViewById(R.id.imageButtonYogurt);
        imageButtonWater = findViewById(R.id.imageButtonWater);

        // Configurar listeners para cada botón usando un único método
        imageButtonJuice.setOnClickListener(view -> handleButtonClick(mediaPlayerJuice, "Me podrias dar un Jugo Por favor"));
        imageButtonTea.setOnClickListener(view -> handleButtonClick(mediaPlayerTea, "Me podrias dar un Té Por favor"));
        imageButtonMilk.setOnClickListener(view -> handleButtonClick(mediaPlayerMilk, "Me podrias dar una Leche Por favor"));
        imagenButtonYogurt.setOnClickListener(view -> handleButtonClick(mediaPlayerYogurt, "Me podrias dar un Yogurt Por favor"));
        imageButtonWater.setOnClickListener(view -> handleButtonClick(mediaPlayerWater, "Me podrias dar una Agua Por favor"));

        // Botón para volver a la actividad anterior
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> finish());
    }

    // Método para manejar clic del botón: cambiar el texto, reproducir sonido y leer la oración
    private void handleButtonClick(MediaPlayer mediaPlayer, String text) {
        // Cambiar el texto en el TextView
        textView.setText(text);

        // Reproducir el sonido
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        // Esperar a que termine el sonido antes de leer la oración
        mediaPlayer.setOnCompletionListener(mp -> {
            // Leer el texto completo usando TextToSpeech
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Liberar recursos de MediaPlayers
        releaseMediaPlayer(mediaPlayerJuice);
        releaseMediaPlayer(mediaPlayerTea);
        releaseMediaPlayer(mediaPlayerMilk);
        releaseMediaPlayer(mediaPlayerYogurt);
        releaseMediaPlayer(mediaPlayerWater);


        // Liberar recursos de TextToSpeech
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}