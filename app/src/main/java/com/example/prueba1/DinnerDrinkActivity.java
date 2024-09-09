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

public class DinnerDrinkActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerJuice, mediaPlayerTea, mediaPlayerMilk, mediaPlayerNothing, mediaPlayerYogurt, mediaPlayerWater;
    private TextView textView;
    private ImageButton imageButtonJuice, imageButtonTea, imageButtonNothing, imageButtonMilk, imageButtonYogurt, imageButtonWater;
    private TextToSpeech textToSpeech;
    private String selectedDrink = "";

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
        mediaPlayerNothing = MediaPlayer.create(this, R.raw.nothing_drink_sound);


        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonJuice = findViewById(R.id.imageButtonJuice);
        imageButtonTea = findViewById(R.id.imageButtonTea);
        imageButtonMilk = findViewById(R.id.imageButtonMilk);
        imageButtonYogurt = findViewById(R.id.imageButtonYogurt);
        imageButtonWater = findViewById(R.id.imageButtonWater);
        imageButtonNothing = findViewById(R.id.imageButtonNothing);

        // Configurar listeners para cada botón usando un único método
        imageButtonJuice.setOnClickListener(view -> handleButtonClick(mediaPlayerJuice, "Me podrías dar un Jugo por favor", "Quiero un Jugo"));
        imageButtonTea.setOnClickListener(view -> handleButtonClick(mediaPlayerTea, "Me podrías dar un Té por favor", "Quiero un Té"));
        imageButtonMilk.setOnClickListener(view -> handleButtonClick(mediaPlayerMilk, "Me podrías dar una Leche por favor", "Quiero una Leche"));
        imageButtonYogurt.setOnClickListener(view -> handleButtonClick(mediaPlayerYogurt, "Me podrías dar un Yogurt por favor", "Quiero un Yogurt"));
        imageButtonWater.setOnClickListener(view -> handleButtonClick(mediaPlayerWater, "Me podrías dar Agua por favor", "Quiero Agua"));
        imageButtonNothing.setOnClickListener(view -> handleButtonClick(mediaPlayerNothing, "La verdad es que no quiero bebestibles hoy ", "No quiero nada para beber"));


        // Botón "Volver" para regresar a la actividad anterior
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> {
            // Limpiar selección de bebida en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("selectedDrink");
            editor.remove("selectedFood"); // Limpiar también la selección de comida
            editor.apply();

            // Limpiar el texto en el TextView
            textView.setText("");

            // Finalizar la actividad
            finish();
        });

        // Botón "Siguiente" para ir a la pantalla de comida
        Button nextButton = findViewById(R.id.btnSiguiente);
        nextButton.setOnClickListener(v -> {
            // Guardar selección en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selectedDrink", selectedDrink);
            editor.apply();

            Intent intent = new Intent(DinnerDrinkActivity.this, DinnerFoodActivity.class);
            intent.putExtra("firstSelection", "drink"); // Indicar que primero se eligió bebida
            startActivity(intent);
        });
    }

    // Método para manejar clic del botón: cambiar el texto, reproducir sonido y leer la oración
    private void handleButtonClick(MediaPlayer mediaPlayer, String text, String drink) {
        // Cambiar el texto en el TextView
        textView.setText(text);
        selectedDrink = drink;

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