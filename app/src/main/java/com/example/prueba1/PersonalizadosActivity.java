package com.example.prueba1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalizadosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPersonalizados;
    private PersonalizadoAdapter personalizadoAdapter;
    private List<MediaItem> personalizadoList;  // Cambiar a MediaItem
    private DatabaseReference mDatabase;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizados);

        // Inicializar el RecyclerView
        recyclerViewPersonalizados = findViewById(R.id.recyclerViewPersonalizados);
        recyclerViewPersonalizados.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista y el adaptador
        personalizadoList = new ArrayList<>();
        personalizadoAdapter = new PersonalizadoAdapter(this, personalizadoList);  // Usar MediaItemAdapter con MediaItem
        recyclerViewPersonalizados.setAdapter(personalizadoAdapter);

        // Inicializar Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("media");

        // Cargar los elementos personalizados desde Firebase
        cargarDatosDesdeFirebase();

        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar la actividad actual y volver a la anterior
                finish();
            }
        });
    }

    private void cargarDatosDesdeFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    personalizadoList.clear(); // Limpiar la lista antes de agregar los datos
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String nombre = snapshot.child("name").getValue(String.class);
                        String imagenPath = snapshot.child("image_path").getValue(String.class);
                        String audioPath = snapshot.child("audio_path").getValue(String.class);

                        Log.d("FirebaseData", "Nombre: " + nombre);
                        Log.d("FirebaseData", "Imagen Path: " + imagenPath);
                        Log.d("FirebaseData", "Audio Path: " + audioPath);

                        MediaItem mediaItem = new MediaItem(audioPath, imagenPath, nombre);  // Usar MediaItem
                        personalizadoList.add(mediaItem);  // Añadir a la lista de MediaItem
                    }
                    personalizadoAdapter.notifyDataSetChanged(); // Notificar al adaptador para actualizar la lista
                } else {
                    Log.d("FirebaseData", "No hay datos en el nodo 'media'");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseData", "Error al leer los datos", databaseError.toException());
                Toast.makeText(PersonalizadosActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

