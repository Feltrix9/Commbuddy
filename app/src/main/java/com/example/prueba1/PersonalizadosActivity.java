package com.example.prueba1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PersonalizadosActivity extends AppCompatActivity {

    private LinearLayout linearLayoutPersonalizados;
    private DatabaseReference mDatabase;

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

        // Conectar con Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("personalizados");

        // Cargar datos desde Firebase
        cargarPersonalizadosDesdeFirebase();
    }

    private void cargarPersonalizadosDesdeFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar el layout antes de añadir nuevos elementos
                linearLayoutPersonalizados.removeAllViews();

                // Iterar sobre los elementos almacenados en Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> personalizadoData = (HashMap<String, Object>) snapshot.getValue();

                    if (personalizadoData != null) {
                        String nombre = (String) personalizadoData.get("nombre");
                        String imagenResId = (String) personalizadoData.get("imagen");
                        String sonidoResId = (String) personalizadoData.get("sonido");

                        Log.d("Personalizados", "Elemento: " + nombre); // Agregar log para verificar que se obtienen datos

                        // Verificar si los valores no son nulos
                        if (nombre != null && imagenResId != null && sonidoResId != null) {
                            // Crear un objeto personalizado
                            Personalizado personalizado = new Personalizado(nombre, Integer.parseInt(imagenResId), Integer.parseInt(sonidoResId));
                            // Mostrar el elemento en la interfaz
                            agregarElementoPersonalizado(personalizado);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer los datos", databaseError.toException());
                Toast.makeText(PersonalizadosActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarElementoPersonalizado(Personalizado personalizado) {
        // Crear un TextView para mostrar el nombre del personalizado
        TextView textView = new TextView(this);
        textView.setText(personalizado.getNombre());
        textView.setTextSize(18);
        textView.setPadding(16, 16, 16, 16);
        textView.setBackgroundResource(R.drawable.rounded_button); // Añadir borde redondeado si lo deseas
        textView.setTextColor(getResources().getColor(android.R.color.black)); // Cambiar color si es necesario

        // Agregar la vista al layout
        linearLayoutPersonalizados.addView(textView);

        // También puedes agregar más vistas, como imágenes o botones si es necesario
        // Ejemplo: agregar imagen
        // ImageView imageView = new ImageView(this);
        // imageView.setImageResource(personalizado.getImagenResId());
        // linearLayoutPersonalizados.addView(imageView);
    }
}

