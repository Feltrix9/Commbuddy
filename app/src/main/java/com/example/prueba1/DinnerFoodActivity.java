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

import com.example.prueba1.R;
import com.example.prueba1.SummaryActivity;

import java.util.Locale;

public class DinnerFoodActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerBread, mediaPlayerMuffin, mediaPlayerEgg, mediaPlayerApple, mediaPlayerNothing, mediaPlayerSoup, mediaPlayerCookie, mediaPlayerPasta;
    private TextView textView;
    private ImageButton imageButtonBread, imageButtonMuffin, imageButtonEgg, imageButtonApple, imageButtonNothing, imageButtonCookie, imageButtonSoup, imageButtonPasta;
    private TextToSpeech textToSpeech;
    private String selectedFood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinnerfood);

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
        mediaPlayerCookie = MediaPlayer.create(this, R.raw.cookie_sound);
        mediaPlayerSoup = MediaPlayer.create(this, R.raw.soup_sound);
        mediaPlayerPasta = MediaPlayer.create(this, R.raw.pasta_sound);
        mediaPlayerNothing = MediaPlayer.create(this, R.raw.nothing_drink_sound);


        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonBread = findViewById(R.id.imageButtonBread);
        imageButtonMuffin = findViewById(R.id.imageButtonMuffin);
        imageButtonEgg = findViewById(R.id.imageButtonEgg);
        imageButtonApple = findViewById(R.id.imageButtonApple);
        imageButtonCookie = findViewById(R.id.imageButtonCookie);
        imageButtonSoup = findViewById(R.id.imageButtonSoup);
        imageButtonPasta = findViewById(R.id.imageButtonPasta);
        imageButtonNothing = findViewById(R.id.imageButtonNothing);

        // Configurar listeners para cada botón usando un único método
        imageButtonBread.setOnClickListener(view -> handleButtonClick(mediaPlayerBread, "Me podrías dar un Pan por favor", "Quiero un Pan"));
        imageButtonMuffin.setOnClickListener(view -> handleButtonClick(mediaPlayerMuffin, "Me podrías dar un Muffin por favor", "Quiero un Muffin"));
        imageButtonEgg.setOnClickListener(view -> handleButtonClick(mediaPlayerEgg, "Me podrías dar un Huevo por favor", "Quiero unos Huevos"));
        imageButtonApple.setOnClickListener(view -> handleButtonClick(mediaPlayerApple, "Me podrías dar una Manzana por favor", "Quiero una Manzana"));
        imageButtonCookie.setOnClickListener(view -> handleButtonClick(mediaPlayerCookie, "Me podrías dar una Galleta por favor", "Quiero una Galleta"));
        imageButtonSoup.setOnClickListener(view -> handleButtonClick(mediaPlayerSoup, "Me podrías dar una Sopa por favor", "Quiero una Sopa"));
        imageButtonPasta.setOnClickListener(view -> handleButtonClick(mediaPlayerPasta, "Me podrías dar Fideos por favor", "Quiero unos Fideos"));
        imageButtonNothing.setOnClickListener(view -> handleButtonClick(mediaPlayerNothing, "La verdad es que no quiero Comer en la cena ", "No quiero comida"));


        // Botón "Volver" para regresar a la actividad anterior
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> {
            // Limpiar selección en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("selectedFood"); // Limpiar la selección de comida
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

            // Verificar la selección previa
            SharedPreferences prefs = getSharedPreferences("UserSelections", MODE_PRIVATE);
            String selectedDrink = prefs.getString("selectedDrink", "");

            Intent intent = new Intent(com.example.prueba1.DinnerFoodActivity.this, SummaryActivity.class);
            intent.putExtra("selectedFood", selectedFood);
            intent.putExtra("selectedDrink", selectedDrink);
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
        releaseMediaPlayer(mediaPlayerBread);
        releaseMediaPlayer(mediaPlayerMuffin);
        releaseMediaPlayer(mediaPlayerEgg);
        releaseMediaPlayer(mediaPlayerApple);
        releaseMediaPlayer(mediaPlayerNothing);
        releaseMediaPlayer(mediaPlayerCookie);
        releaseMediaPlayer(mediaPlayerSoup);
        releaseMediaPlayer(mediaPlayerPasta);

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

