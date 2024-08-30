package com.example.prueba1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {

    private ImageButton btnBreakfast, btnLunch, btnDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar botones
        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);

        // Configurar listeners para abrir nuevas actividades
        btnBreakfast.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BreakfastActivity.class);
            startActivity(intent);
        });

        btnLunch.setOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Almuerzo seleccionado", Toast.LENGTH_SHORT).show());

        btnDinner.setOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Cena seleccionada", Toast.LENGTH_SHORT).show());
    }
}
