package com.example.prueba1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BreakfastFoodActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerBread, mediaPlayerMuffin, mediaPlayerEgg, mediaPlayerApple;
    private TextView textView;
    private ImageButton imageButtonBread, imageButtonMuffin, imageButtonEgg, imageButtonApple;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfastfood);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("es", "MX"));
            }
        });

        // Inicializar MediaPlayer para cada botón
        mediaPlayerBread = MediaPlayer.create(this, R.raw.bread_sound);
        mediaPlayerMuffin = MediaPlayer.create(this, R.raw.muffin_sound);
        mediaPlayerEgg = MediaPlayer.create(this, R.raw.egg_sound);
        mediaPlayerApple = MediaPlayer.create(this, R.raw.apple_sound);

        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonBread = findViewById(R.id.imageButtonBread);
        imageButtonMuffin = findViewById(R.id.imageButtonMuffin);
        imageButtonEgg = findViewById(R.id.imageButtonEgg);
        imageButtonApple = findViewById(R.id.imageButtonApple);

        // Configurar listeners para cada botón usando un único método
        imageButtonBread.setOnClickListener(view -> handleButtonClick(mediaPlayerBread, "Me podrias dar un Pan Por favor"));
        imageButtonMuffin.setOnClickListener(view -> handleButtonClick(mediaPlayerMuffin, "Me podrias dar un Muffin Por favor"));
        imageButtonEgg.setOnClickListener(view -> handleButtonClick(mediaPlayerEgg, "Me podrias dar un Huevo Por favor"));
        imageButtonApple.setOnClickListener(view -> handleButtonClick(mediaPlayerApple, "Me podrias dar una Manzana Por favor"));

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
        releaseMediaPlayer(mediaPlayerBread);
        releaseMediaPlayer(mediaPlayerMuffin);
        releaseMediaPlayer(mediaPlayerEgg);
        releaseMediaPlayer(mediaPlayerApple);

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
