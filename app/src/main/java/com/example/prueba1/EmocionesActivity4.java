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

public class EmocionesActivity4 extends AppCompatActivity {

        private MediaPlayer mediaPlayerAdolorido, mediaPlayerApenado, mediaPlayerCalmado, mediaPlayerContento;
        private TextView textView;
        private ImageButton imageButtonAdolorido, imageButtonApenado, imageButtonCalmado, imageButtonContento, imageButtonDisgustado, imageButtonDesesperado, imageButtonDepresivo, imageButtonAngustiado, imageButtonDecepcionado;
        private TextToSpeech textToSpeech;
        private String selectedEmotion = ""; // Variable para almacenar emoción seleccionada

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_emociones4);

            // Inicializar TextToSpeech
            textToSpeech = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(new Locale("es", "MX"));
                }
            });

            // Inicializar MediaPlayer para cada emoción
            mediaPlayerAdolorido = MediaPlayer.create(this, R.raw.adolorido_sound);
            mediaPlayerApenado = MediaPlayer.create(this, R.raw.apenado_sound);
            mediaPlayerCalmado = MediaPlayer.create(this, R.raw.calmado_sound);
            mediaPlayerContento = MediaPlayer.create(this, R.raw.contento_sound);

            // Inicializar TextView e ImageButtons
            textView = findViewById(R.id.textViewChat);
            imageButtonAdolorido = findViewById(R.id.imageButtonAdolorido);
            imageButtonApenado = findViewById(R.id.imageButtonApenado);
            imageButtonCalmado = findViewById(R.id.imageButtonCalmado);
            imageButtonContento = findViewById(R.id.imageButtonContento);


            // Configurar listeners para cada botón con un único método
            imageButtonAdolorido.setOnClickListener(view -> handleButtonClick(mediaPlayerAdolorido, "Hoy Estoy algo Adolorido.", "Adolorido"));
            imageButtonApenado.setOnClickListener(view -> handleButtonClick(mediaPlayerApenado, "Hoy Estoy algo Apenado.", "Apenado"));
            imageButtonCalmado.setOnClickListener(view -> handleButtonClick(mediaPlayerCalmado, "Hoy Estoy algo Calmado.", "Calmado"));
            imageButtonContento.setOnClickListener(view -> handleButtonClick(mediaPlayerContento, "Hoy Estoy algo Contento.", "Contento"));

            Button backButtonLobby = findViewById(R.id.btnVolverLobby);
            backButtonLobby.setOnClickListener(v -> {
                // Crear un Intent para ir a LobbyActivity
                Intent intent = new Intent(com.example.prueba1.EmocionesActivity4.this, LobbyActivity.class);
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
            releaseMediaPlayer(mediaPlayerAdolorido);
            releaseMediaPlayer(mediaPlayerApenado);
            releaseMediaPlayer(mediaPlayerCalmado);
            releaseMediaPlayer(mediaPlayerContento);

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
