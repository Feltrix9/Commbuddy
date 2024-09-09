package com.example.prueba1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LunchFoodActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerMeat, mediaPlayerPasta, mediaPlayerRice, mediaPlayerEgg, mediaPlayersoup, mediaPlayerMash_Potatoes, mediaPlayerLettuce ;
    private TextView textView;
    private ImageButton imageButtonMeat, imageButtonPasta, imageButtonRice, imageButtonMash_Potatoes, imageButtonEgg, imageButtonSoup, imageButtonLettuce;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunchfood);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("es", "MX"));
            }
        });

        // Inicializar MediaPlayer para cada botón

        mediaPlayerMeat = MediaPlayer.create(this, R.raw.meat_sound);
        mediaPlayerPasta = MediaPlayer.create(this, R.raw.pasta_sound);
        mediaPlayerRice = MediaPlayer.create(this, R.raw.rice_sound);
        mediaPlayerMash_Potatoes = MediaPlayer.create(this, R.raw.mash_potatoes_sound);
        mediaPlayerEgg = MediaPlayer.create(this, R.raw.egg_sound);
        mediaPlayersoup = MediaPlayer.create(this, R.raw.soup_sound);
        mediaPlayerLettuce = MediaPlayer.create(this, R.raw.lettuce_sound);


        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonMeat = findViewById(R.id.imageButtonMeat);
        imageButtonPasta = findViewById(R.id.imageButtonPasta);
        imageButtonRice = findViewById(R.id.imageButtonRice);
        imageButtonMash_Potatoes = findViewById(R.id.imageButtonMash_potatoes);
        imageButtonEgg = findViewById(R.id.imageButtonEgg);
        imageButtonSoup = findViewById(R.id.imageButtonSoup);
        imageButtonLettuce = findViewById(R.id.imageButtonLettuce);


        // Configurar listeners para cada botón usando un único método
        imageButtonMeat.setOnClickListener(view -> handleButtonClick(mediaPlayerMeat, "Me podrias dar Carne Por favor"));
        imageButtonPasta.setOnClickListener(view -> handleButtonClick(mediaPlayerPasta, "Me podrias dar  Fideos Por favor"));
        imageButtonRice.setOnClickListener(view -> handleButtonClick(mediaPlayerRice, "Me podrias dar  Arroz Por favor"));
        imageButtonMash_Potatoes.setOnClickListener(view -> handleButtonClick(mediaPlayerMash_Potatoes, "Me podrias dar Pure de papas Por favor"));
        imageButtonEgg.setOnClickListener(view -> handleButtonClick(mediaPlayerEgg, "Me podrias dar unos Huevos Por favor"));
        imageButtonSoup.setOnClickListener(view -> handleButtonClick(mediaPlayersoup, "Me podrias dar una Sopa Por favor"));
        imageButtonLettuce.setOnClickListener(view -> handleButtonClick(mediaPlayerLettuce, "Me podrias dar Lechuga Por favor"));

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
        releaseMediaPlayer(mediaPlayerMeat);
        releaseMediaPlayer(mediaPlayerPasta);
        releaseMediaPlayer(mediaPlayerRice);
        releaseMediaPlayer(mediaPlayerMash_Potatoes);
        releaseMediaPlayer(mediaPlayerEgg);
        releaseMediaPlayer(mediaPlayersoup);
        releaseMediaPlayer(mediaPlayerLettuce);



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
