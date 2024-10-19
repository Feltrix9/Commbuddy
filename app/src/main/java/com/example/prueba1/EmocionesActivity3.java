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

public class EmocionesActivity3 extends AppCompatActivity {

        private MediaPlayer mediaPlayerMalHumorado, mediaPlayerAburrido, mediaPlayerDistraido, mediaPlayerIncredulo, mediaPlayerDisgustado, mediaPlayerDesesperado, mediaPlayerDepresivo, mediaPlayerAngustiado, mediaPlayerDecepcionado;
        private TextView textView;
        private ImageButton imageButtonMalHumorado, imageButtonAburrido, imageButtonDistraido, imageButtonIncredulo, imageButtonDisgustado, imageButtonDesesperado, imageButtonDepresivo, imageButtonAngustiado, imageButtonDecepcionado;
        private TextToSpeech textToSpeech;
        private String selectedEmotion = ""; // Variable para almacenar emoción seleccionada

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_emociones3);

            // Inicializar TextToSpeech
            textToSpeech = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("es", "MX"));
                }
            });

            // Inicializar MediaPlayer para cada emoción
            mediaPlayerMalHumorado = MediaPlayer.create(this, R.raw.malhumorado_sound);
            mediaPlayerAburrido = MediaPlayer.create(this, R.raw.aburrido_sound);
            mediaPlayerDistraido = MediaPlayer.create(this, R.raw.distraido_sound);
            mediaPlayerIncredulo = MediaPlayer.create(this, R.raw.incredulo_sound);
            mediaPlayerDisgustado = MediaPlayer.create(this, R.raw.disgusto_sound);
            mediaPlayerDesesperado = MediaPlayer.create(this, R.raw.desesperado_sound);
            mediaPlayerDepresivo = MediaPlayer.create(this, R.raw.depresivo_sound);
            mediaPlayerAngustiado = MediaPlayer.create(this, R.raw.angustiado_sound);
            mediaPlayerDecepcionado = MediaPlayer.create(this, R.raw.decepcionado_sound);

            // Inicializar TextView e ImageButtons
            textView = findViewById(R.id.textViewChat);
            imageButtonMalHumorado = findViewById(R.id.imageButtonMalhumorado);
            imageButtonAburrido = findViewById(R.id.imageButtonAburrido);
            imageButtonDistraido = findViewById(R.id.imageButtonDistraido);
            imageButtonIncredulo = findViewById(R.id.imageButtonIncredulo);
            imageButtonDisgustado = findViewById(R.id.imageButtonDisgusto);
            imageButtonDesesperado = findViewById(R.id.imageButtonDesesperado);
            imageButtonDepresivo = findViewById(R.id.imageButtonDepresivo);
            imageButtonAngustiado = findViewById(R.id.imageButtonAngustiado);
            imageButtonDecepcionado = findViewById(R.id.imageButtonDecepcionado);

            // Configurar listeners para cada botón con un único método
            imageButtonMalHumorado.setOnClickListener(view -> handleButtonClick(mediaPlayerMalHumorado, "Hoy Estoy algo MalHumorado.", "MalHumorado"));
            imageButtonAburrido.setOnClickListener(view -> handleButtonClick(mediaPlayerAburrido, "Hoy Estoy algo Aburrido.", "Aburrido"));
            imageButtonDistraido.setOnClickListener(view -> handleButtonClick(mediaPlayerDistraido, "Hoy Estoy algo Distraido.", "Distraido"));
            imageButtonIncredulo.setOnClickListener(view -> handleButtonClick(mediaPlayerIncredulo, "Hoy Estoy algo Incredulo.", "Incredulo"));
            imageButtonDisgustado.setOnClickListener(view -> handleButtonClick(mediaPlayerDisgustado, "Hoy Estoy algo Disgustado.", "Disgustado"));
            imageButtonDesesperado.setOnClickListener(view -> handleButtonClick(mediaPlayerDesesperado, "Hoy Estoy Desesperado.", "Desesperado"));
            imageButtonDepresivo.setOnClickListener(view -> handleButtonClick(mediaPlayerDepresivo, "Hoy Estoy algo Depresivo.", "Depresivo"));
            imageButtonAngustiado.setOnClickListener(view -> handleButtonClick(mediaPlayerAngustiado, "Hoy Estoy algo Angustiado.", "Angustiado"));
            imageButtonDecepcionado.setOnClickListener(view -> handleButtonClick(mediaPlayerDecepcionado, "Hoy Estoy algo Decepcionado.", "Decepcionado"));

            // Botón "Siguiente" para pasar a la siguiente actividad
            Button btnSiguiente = findViewById(R.id.btnSiguiente);
            btnSiguiente.setOnClickListener(v -> {
                // Guardar la emoción seleccionada en SharedPreferences
                SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selectedEmotion", selectedEmotion);
                editor.apply();

                // Iniciar la siguiente actividad
                Intent intent = new Intent(com.example.prueba1.EmocionesActivity3.this, EmocionesActivity4.class);
                intent.putExtra("selectedEmotion", selectedEmotion);
                startActivity(intent);
            });

            Button backButtonLobby = findViewById(R.id.btnVolverLobby);
            backButtonLobby.setOnClickListener(v -> {
                // Crear un Intent para ir a LobbyActivity
                Intent intent = new Intent(com.example.prueba1.EmocionesActivity3.this, LobbyActivity.class);
                // Iniciar la nueva actividad
                startActivity(intent);
                // Opcionalmente, cerrar la actividad actual
                finish();
            });

            // Botón "Volver" para regresar a la actividad anterior
            Button backButton = findViewById(R.id.btnVolver);
            backButton.setOnClickListener(v -> finish());
        }

        // Método para manejar clic del botón: cambiar el texto, reproducir sonido y guardar emoción
        private void handleButtonClick(MediaPlayer mediaPlayer, String text, String emotion) {
            textView.setText(text);
            selectedEmotion = emotion;

            if (mediaPlayer != null) {
                mediaPlayer.start();
            }

            mediaPlayer.setOnCompletionListener(mp -> {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            releaseMediaPlayer(mediaPlayerMalHumorado);
            releaseMediaPlayer(mediaPlayerAburrido);
            releaseMediaPlayer(mediaPlayerAngustiado);
            releaseMediaPlayer(mediaPlayerDesesperado);
            releaseMediaPlayer(mediaPlayerDepresivo);
            releaseMediaPlayer(mediaPlayerDecepcionado);
            releaseMediaPlayer(mediaPlayerDisgustado);
            releaseMediaPlayer(mediaPlayerIncredulo);
            releaseMediaPlayer(mediaPlayerDistraido);

            if (textToSpeech != null) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        }

        private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        }
    }
