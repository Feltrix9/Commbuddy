package com.example.prueba1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LunchFoodActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerMeat, mediaPlayerPasta, mediaPlayerNothing, mediaPlayerRice, mediaPlayerEgg, mediaPlayersoup, mediaPlayerMash_Potatoes, mediaPlayerLettuce;
    private TextView textView;
    private ImageButton imageButtonMeat, imageButtonPasta, imageButtonRice, imageButtonNothing, imageButtonMash_Potatoes, imageButtonEgg, imageButtonSoup, imageButtonLettuce;
    private TextToSpeech textToSpeech;
    private String selectedFood = "";

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
        mediaPlayerNothing = MediaPlayer.create(this, R.raw.nothing_drink_sound);


        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonMeat = findViewById(R.id.imageButtonMeat);
        imageButtonPasta = findViewById(R.id.imageButtonPasta);
        imageButtonRice = findViewById(R.id.imageButtonRice);
        imageButtonMash_Potatoes = findViewById(R.id.imageButtonMash_potatoes);
        imageButtonEgg = findViewById(R.id.imageButtonEgg);
        imageButtonSoup = findViewById(R.id.imageButtonSoup);
        imageButtonLettuce = findViewById(R.id.imageButtonLettuce);
        imageButtonNothing = findViewById(R.id.imageButtonNothing);


        // Configurar listeners para cada botón usando un único método
        imageButtonMeat.setOnClickListener(view -> handleButtonClick(mediaPlayerMeat, "Me podrías dar Carne por favor", "Quiero Almorzar Carne"));
        imageButtonPasta.setOnClickListener(view -> handleButtonClick(mediaPlayerPasta, "Me podrías dar Fideos por favor", "Quiero Almorzar Fideos"));
        imageButtonRice.setOnClickListener(view -> handleButtonClick(mediaPlayerRice, "Me podrías dar Arroz por favor", "Quiero Almorzar Arroz"));
        imageButtonMash_Potatoes.setOnClickListener(view -> handleButtonClick(mediaPlayerMash_Potatoes, "Me podrías dar Puré de papas por favor", "Quiero Almorzar Puré de papas"));
        imageButtonEgg.setOnClickListener(view -> handleButtonClick(mediaPlayerEgg, "Me podrías dar unos Huevos por favor", "Quiero Almorzar Huevos"));
        imageButtonSoup.setOnClickListener(view -> handleButtonClick(mediaPlayersoup, "Me podrías dar una Sopa por favor", "Quiero Almorzar Sopa"));
        imageButtonLettuce.setOnClickListener(view -> handleButtonClick(mediaPlayerLettuce, "Me podrías dar Lechuga por favor", "Quiero Almorzar Lechuga"));
        imageButtonNothing.setOnClickListener(view -> handleButtonClick(mediaPlayerNothing, "La verdad es que no quiero Comer en el almuerzo ", "No quiero comida"));

        // Botón "Volver" para regresar a la actividad anterior
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> {
            // Limpiar selección de comida en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("selectedFood");
            editor.apply();

            // Limpiar el texto en el TextView
            textView.setText("");

            // Finalizar la actividad
            finish();
        });

        // Botón "Siguiente" para ir a la pantalla de resumen
        Button nextButton = findViewById(R.id.btnSiguiente);
        nextButton.setOnClickListener(v -> {
            // Guardar selección en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selectedFood", selectedFood);
            editor.apply();

            Intent intent = new Intent(LunchFoodActivity.this, SummaryActivity.class);
            intent.putExtra("firstSelection", "food"); // Indicar que se eligió comida
            startActivity(intent);
        });
    }

    // Método para manejar clic del botón: cambiar el texto, reproducir sonido y leer la oración
    private void handleButtonClick(MediaPlayer mediaPlayer, String text, String food) {
        // Cambiar el texto en el TextView
        textView.setText(text);
        selectedFood = food;

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
        releaseMediaPlayer(mediaPlayerNothing);

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

