package com.example.prueba1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PersonalizadosActivity extends AppCompatActivity {

    private LinearLayout linearLayoutPersonalizados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizados);

        // Inicializar el layout donde se mostrarán los elementos personalizados
        linearLayoutPersonalizados = findViewById(R.id.linearLayoutPersonalizados);

        // Botón para volver a la actividad anterior
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra esta actividad y regresa a la anterior
            }
        });

        // Obtener los elementos personalizados del CRUD
        List<Personalizado> personalizados = PersonalizadoRepository.getInstance().getPersonalizados();

        // Mostrar los elementos personalizados en la interfaz de usuario
        for (Personalizado personalizado : personalizados) {
            agregarElementoPersonalizado(personalizado);
        }
    }

    private void agregarElementoPersonalizado(Personalizado personalizado) {
        // Crear una vista para mostrar el nombre del personalizado
        TextView textView = new TextView(this);
        textView.setText(personalizado.getNombre());
        textView.setTextSize(18);
        textView.setPadding(0, 16, 0, 16);

        // Agregar la vista al layout
        linearLayoutPersonalizados.addView(textView);

        // Aquí puedes agregar más lógica para mostrar imágenes, sonidos, etc.
    }
}
