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

public class EmocionesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerFeliz, mediaPlayerTriste, mediaPlayerEnojado, mediaPlayerAsustado, mediaPlayerAsqueado, mediaPlayerSatisfecho, mediaPlayerEntusiasta, mediaPlayerEmocionado, mediaPlayerInteresado;
    private TextView textView;
    private ImageButton imageButtonFeliz, imageButtonTriste, imageButtonEnojado, imageButtonAsustado, imageButtonAsqueado, imageButtonSatisfecho, imageButtonEntusiasta, imageButtonEmocionado, imageButtonInteresado;
    private TextToSpeech textToSpeech;
    private String selectedEmotion = ""; // Variable para almacenar emoción seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emociones1);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("es", "MX"));
            }
        });

        // Inicializar MediaPlayer para cada emoción
        mediaPlayerFeliz = MediaPlayer.create(this, R.raw.feliz_sound);
        mediaPlayerTriste = MediaPlayer.create(this, R.raw.triste_sound);
        mediaPlayerEnojado = MediaPlayer.create(this, R.raw.enojado_sound);
        mediaPlayerAsustado = MediaPlayer.create(this, R.raw.asustado_sound);
        mediaPlayerAsqueado = MediaPlayer.create(this, R.raw.asqueado_sound);
        mediaPlayerSatisfecho = MediaPlayer.create(this, R.raw.satisfecho_sound);
        mediaPlayerEntusiasta = MediaPlayer.create(this, R.raw.entusiasta_sound);
        mediaPlayerEmocionado = MediaPlayer.create(this, R.raw.emocionado_sound);
        mediaPlayerInteresado = MediaPlayer.create(this, R.raw.interesado_sound);

        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonFeliz = findViewById(R.id.imageButtonFeliz);
        imageButtonTriste = findViewById(R.id.imageButtonTriste);
        imageButtonEnojado = findViewById(R.id.imageButtonEnojado);
        imageButtonAsustado = findViewById(R.id.imageButtonAsustado);
        imageButtonAsqueado = findViewById(R.id.imageButtonAsqueado);
        imageButtonSatisfecho = findViewById(R.id.imageButtonSatisfecho);
        imageButtonEntusiasta = findViewById(R.id.imageButtonEntusiasta);
        imageButtonEmocionado = findViewById(R.id.imageButtonEmocionado);
        imageButtonInteresado = findViewById(R.id.imageButtonInteresado);

        // Configurar listeners para cada botón con un único método
        imageButtonFeliz.setOnClickListener(view -> handleButtonClick(mediaPlayerFeliz, "Hoy Estoy algo feliz.", "Feliz"));
        imageButtonTriste.setOnClickListener(view -> handleButtonClick(mediaPlayerTriste, "Hoy Estoy algo triste.", "Triste"));
        imageButtonEnojado.setOnClickListener(view -> handleButtonClick(mediaPlayerEnojado, "Hoy Estoy algo enojado.", "Enojado"));
        imageButtonAsustado.setOnClickListener(view -> handleButtonClick(mediaPlayerAsustado, "Hoy Estoy algo asustado.", "Asustado"));
        imageButtonAsqueado.setOnClickListener(view -> handleButtonClick(mediaPlayerAsqueado, "Hoy Estoy algo asqueado.", "Asqueado"));
        imageButtonSatisfecho.setOnClickListener(view -> handleButtonClick(mediaPlayerSatisfecho, "Hoy Estoy satisfecho.", "Satisfecho"));
        imageButtonEntusiasta.setOnClickListener(view -> handleButtonClick(mediaPlayerEntusiasta, "Hoy Estoy algo entusiasta.", "Entusiasta"));
        imageButtonEmocionado.setOnClickListener(view -> handleButtonClick(mediaPlayerEmocionado, "Hoy Estoy algo emocionado.", "Emocionado"));
        imageButtonInteresado.setOnClickListener(view -> handleButtonClick(mediaPlayerInteresado, "Hoy Estoy algo interesado en aprender algo.", "Interesado"));

        // Botón "Siguiente" para pasar a la siguiente actividad
        Button btnSiguiente = findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(v -> {
            // Guardar la emoción seleccionada en SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selectedEmotion", selectedEmotion);
            editor.apply();

            // Iniciar la siguiente actividad
            Intent intent = new Intent(EmocionesActivity.this, EmocionesActivity2.class);
            intent.putExtra("selectedEmotion", selectedEmotion);
            startActivity(intent);
        });

        // Botón "Volver" para regresar a la actividad anterior
        Button backButton = findViewById(R.id.btnVolverLobby);
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
        releaseMediaPlayer(mediaPlayerFeliz);
        releaseMediaPlayer(mediaPlayerTriste);
        releaseMediaPlayer(mediaPlayerEnojado);
        releaseMediaPlayer(mediaPlayerAsustado);
        releaseMediaPlayer(mediaPlayerAsqueado);
        releaseMediaPlayer(mediaPlayerSatisfecho);
        releaseMediaPlayer(mediaPlayerEntusiasta);
        releaseMediaPlayer(mediaPlayerEmocionado);
        releaseMediaPlayer(mediaPlayerInteresado);

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

