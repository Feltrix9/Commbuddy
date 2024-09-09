package com.example.prueba1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SummaryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView textViewSummary;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewSummary = findViewById(R.id.textViewSummary);

        // Inicializar TextToSpeech
        textToSpeech = new TextToSpeech(this, this);

        // Recuperar selecciones de SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserSelections", MODE_PRIVATE);
        String selectedDrink = preferences.getString("selectedDrink", "No seleccionado");
        String selectedFood = preferences.getString("selectedFood", "No seleccionado");

        // Mostrar el resumen
        String summary = "Hoy  " + selectedDrink + " y  " + selectedFood + " por favor.";
        textViewSummary.setText(summary);

        textViewSummary.setOnClickListener(v -> {
            // Reproducir el mensaje cuando se presione el TextView
            speakOut(summary);
        });

        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpiar la pila de actividades
            startActivity(intent);
            finish(); // Finalizar la actividad actual
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Configurar el idioma a Español (México)
            int langResult = textToSpeech.setLanguage(new Locale("es", "MX"));
            if (langResult == TextToSpeech.LANG_MISSING_DATA ||
                    langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Lenguaje no soportado o datos faltantes
            }
        } else {
            // Fallo en la inicialización
        }
    }

    private void speakOut(@NonNull String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}

