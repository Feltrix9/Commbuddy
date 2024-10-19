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

public class EmocionesActivity2 extends AppCompatActivity {

    private MediaPlayer mediaPlayerAsombrado, mediaPlayerSorprendido, mediaPlayerAterrorizado, mediaPlayerPreocupado, mediaPlayerNervioso, mediaPlayerAnsioso, mediaPlayerFrustrado, mediaPlayerIrritado, mediaPlayerConfundido;
    private TextView textView;
    private ImageButton imageButtonAsombrado, imageButtonSorprendido, imageButtonAterrorizado, imageButtonPreocupado, imageButtonNervioso, imageButtonAnsioso, imageButtonFrustrado, imageButtonIrritado, imageButtonConfundido;
    private TextToSpeech textToSpeech;
    private String selectedEmotion = ""; // Variable para almacenar emoción seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emociones2);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("es", "MX"));
            }
        });

        // Inicializar MediaPlayer para cada emoción
        mediaPlayerAsombrado = MediaPlayer.create(this, R.raw.asombrado_sound);
        mediaPlayerSorprendido = MediaPlayer.create(this, R.raw.sorprendido_sound);
        mediaPlayerAterrorizado = MediaPlayer.create(this, R.raw.aterrorizado_sound);
        mediaPlayerPreocupado = MediaPlayer.create(this, R.raw.preocupado_sound);
        mediaPlayerNervioso = MediaPlayer.create(this, R.raw.nervioso_sound);
        mediaPlayerAnsioso = MediaPlayer.create(this, R.raw.ansioso_sound);
        mediaPlayerFrustrado = MediaPlayer.create(this, R.raw.frustrado_sound);
        mediaPlayerIrritado = MediaPlayer.create(this, R.raw.irritado_sound);
        mediaPlayerConfundido = MediaPlayer.create(this, R.raw.confundido_sound);

        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonAsombrado = findViewById(R.id.imageButtonAsombrado);
        imageButtonSorprendido = findViewById(R.id.imageButtonSorprendido);
        imageButtonAterrorizado = findViewById(R.id.imageButtonAterrorizado);
        imageButtonPreocupado = findViewById(R.id.imageButtonPreocupado);
        imageButtonNervioso = findViewById(R.id.imageButtonNervioso);
        imageButtonAnsioso = findViewById(R.id.imageButtonAnsioso);
        imageButtonFrustrado = findViewById(R.id.imageButtonFrustrado);
        imageButtonIrritado = findViewById(R.id.imageButtonIrritado);
        imageButtonConfundido = findViewById(R.id.imageButtonConfundido);

        // Configurar listeners para cada botón con un único método
        imageButtonAsombrado.setOnClickListener(view -> handleButtonClick(mediaPlayerAsombrado, "Hoy Estoy algo asombrado.", "Asombrado"));
        imageButtonSorprendido.setOnClickListener(view -> handleButtonClick(mediaPlayerSorprendido, "Hoy Estoy algo Sorprendido.", "Sorprendido"));
        imageButtonAterrorizado.setOnClickListener(view -> handleButtonClick(mediaPlayerAterrorizado, "Hoy Estoy algo aterrorizado.", "Aterrorizado"));
        imageButtonPreocupado.setOnClickListener(view -> handleButtonClick(mediaPlayerPreocupado, "Hoy Estoy algo Preocupado.", "Preocupado"));
        imageButtonNervioso.setOnClickListener(view -> handleButtonClick(mediaPlayerNervioso, "Hoy Estoy algo nervioso.", "Nervioso"));
        imageButtonAnsioso.setOnClickListener(view -> handleButtonClick(mediaPlayerAnsioso, "Hoy Estoy ansioso.", "Ansioso"));
        imageButtonFrustrado.setOnClickListener(view -> handleButtonClick(mediaPlayerFrustrado, "Hoy Estoy algo frustrado.", "Frustrado"));
        imageButtonIrritado.setOnClickListener(view -> handleButtonClick(mediaPlayerIrritado, "Hoy Estoy algo irritado.", "Irritado"));
        imageButtonConfundido.setOnClickListener(view -> handleButtonClick(mediaPlayerConfundido, "Hoy Estoy algo confundido.", "Confundido"));

        // Botón "Siguiente" para pasar a la siguiente actividad
        Button btnSiguiente = findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(v -> {
            // Guardar la emoción seleccionada en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selectedEmotion", selectedEmotion);
            editor.apply();

            // Iniciar la siguiente actividad
            Intent intent = new Intent(EmocionesActivity2.this, EmocionesActivity3.class);
            intent.putExtra("selectedEmotion", selectedEmotion);
            startActivity(intent);
        });

        Button backButtonLobby = findViewById(R.id.btnVolverLobby);
        backButtonLobby.setOnClickListener(v -> {
            // Crear un Intent para ir a LobbyActivity
            Intent intent = new Intent(EmocionesActivity2.this, LobbyActivity.class);
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
        releaseMediaPlayer(mediaPlayerAsombrado);
        releaseMediaPlayer(mediaPlayerSorprendido);
        releaseMediaPlayer(mediaPlayerAterrorizado);
        releaseMediaPlayer(mediaPlayerPreocupado);
        releaseMediaPlayer(mediaPlayerNervioso);
        releaseMediaPlayer(mediaPlayerAnsioso);
        releaseMediaPlayer(mediaPlayerFrustrado);
        releaseMediaPlayer(mediaPlayerIrritado);
        releaseMediaPlayer(mediaPlayerConfundido);

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