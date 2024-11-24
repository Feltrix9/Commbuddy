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


public class AccionesActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayerComer, mediaPlayerDucharse, mediaPlayerHacerEjercicio, mediaPlayerHacerLaCama, mediaPlayerIrAlBano, mediaPlayerLavarseLasManos, mediaPlayerLimpiarLaPieza, mediaPlayerSalirDeLaCasa, mediaPlayerVerTelevision;
    private TextView textView;
    private ImageButton imageButtonComer, imageButtonDucharse, imageButtonHacerEjercicio, imageButtonHacerLaCama, imageButtonIrAlBano, imageButtonLavarseLasManos, imageButtonLimpiarLaPieza, imageButtonSalirDeLaCasa, imageButtonVerTelevision;
    private TextToSpeech textToSpeech;
    private String selectedFood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("es", "MX"));
            }
        });

        // Inicializar MediaPlayer para cada acción
        mediaPlayerComer = MediaPlayer.create(this, R.raw.comer_sound);
        mediaPlayerDucharse = MediaPlayer.create(this, R.raw.ducharse_sound);
        mediaPlayerHacerEjercicio = MediaPlayer.create(this, R.raw.hacer_ejercicio_sound);
        mediaPlayerHacerLaCama = MediaPlayer.create(this, R.raw.hacer_la_cama_sound);
        mediaPlayerIrAlBano = MediaPlayer.create(this, R.raw.ir_al_bano_sound);
        mediaPlayerLavarseLasManos = MediaPlayer.create(this, R.raw.lavarse_las_manos_sound);
        mediaPlayerLimpiarLaPieza = MediaPlayer.create(this, R.raw.limpiar_la_pieza_sound);
        mediaPlayerSalirDeLaCasa = MediaPlayer.create(this, R.raw.salir_de_la_casa_sound);
        mediaPlayerVerTelevision = MediaPlayer.create(this, R.raw.ver_television_sound);

        // Inicializar TextView e ImageButtons
        textView = findViewById(R.id.textViewChat);
        imageButtonComer = findViewById(R.id.imageButtonComer);
        imageButtonDucharse = findViewById(R.id.imageButtonDucharse);
        imageButtonHacerEjercicio = findViewById(R.id.imageButtonHacer_ejercicio);
        imageButtonHacerLaCama = findViewById(R.id.imageButtonhacer_la_cama);
        imageButtonIrAlBano = findViewById(R.id.image_ir_al_bano);
        imageButtonLavarseLasManos = findViewById(R.id.imageButton_lavarse_las_manos);
        imageButtonLimpiarLaPieza = findViewById(R.id.imageButtonlimpiar_la_pieza);
        imageButtonSalirDeLaCasa = findViewById(R.id.imageButtonSalirDeLaCasa);
        imageButtonVerTelevision = findViewById(R.id.imageButtonver_television);

        // Configurar listeners para cada botón
        imageButtonComer.setOnClickListener(view -> handleButtonClick(mediaPlayerComer, "Me podrías dar algo de comer por favor", "Comer"));
        imageButtonDucharse.setOnClickListener(view -> handleButtonClick(mediaPlayerDucharse, "Necesito ducharme", "Ducharse"));
        imageButtonHacerEjercicio.setOnClickListener(view -> handleButtonClick(mediaPlayerHacerEjercicio, "Quiero hacer ejercicio", "Hacer Ejercicio"));
        imageButtonHacerLaCama.setOnClickListener(view -> handleButtonClick(mediaPlayerHacerLaCama, "Voy a hacer la cama", "Hacer la Cama"));
        imageButtonIrAlBano.setOnClickListener(view -> handleButtonClick(mediaPlayerIrAlBano, "Necesito ir al baño", "Ir al Baño"));
        imageButtonLavarseLasManos.setOnClickListener(view -> handleButtonClick(mediaPlayerLavarseLasManos, "Quiero lavarme las manos", "Lavarse las Manos"));
        imageButtonLimpiarLaPieza.setOnClickListener(view -> handleButtonClick(mediaPlayerLimpiarLaPieza, "Quiero limpiar mi pieza", "Limpiar la Pieza"));
        imageButtonSalirDeLaCasa.setOnClickListener(view -> handleButtonClick(mediaPlayerSalirDeLaCasa, "Quiero salir de la casa", "Salir de la Casa"));
        imageButtonVerTelevision.setOnClickListener(view -> handleButtonClick(mediaPlayerVerTelevision, "Voy a ver televisión", "Ver Televisión"));

        // Botón "Volver"
        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> {
            textView.setText("");
            finish();
        });
    }

    // Método para manejar clics
    private void handleButtonClick(MediaPlayer mediaPlayer, String text, String action) {
        textView.setText(text);

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        mediaPlayer.setOnCompletionListener(mp -> textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        releaseMediaPlayer(mediaPlayerComer);
        releaseMediaPlayer(mediaPlayerDucharse);
        releaseMediaPlayer(mediaPlayerHacerEjercicio);
        releaseMediaPlayer(mediaPlayerHacerLaCama);
        releaseMediaPlayer(mediaPlayerIrAlBano);
        releaseMediaPlayer(mediaPlayerLavarseLasManos);
        releaseMediaPlayer(mediaPlayerLimpiarLaPieza);
        releaseMediaPlayer(mediaPlayerSalirDeLaCasa);
        releaseMediaPlayer(mediaPlayerVerTelevision);

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





