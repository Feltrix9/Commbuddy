package com.example.prueba1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {

    private ImageButton btnMeals, btnEmociones, btnAcciones;
    private MediaPlayer mediaPlayerMeals, mediaPlayerEmociones,mediaPlayerAcciones;
    private Button btnGoToCrud, btnPersonalizados;
    private RecyclerView recyclerView;
    private PersonalizadoAdapter personalizadoAdapter;
    private List<Personalizado> personalizadoList;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Inicializar elementos visuales
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("CommBuddy");

        btnMeals = findViewById(R.id.btnmeals);
        btnEmociones = findViewById(R.id.btnEmociones);
        btnAcciones = findViewById(R.id.btnAcciones);



        // Inicializar MediaPlayers con sonidos opcionales
        mediaPlayerMeals = MediaPlayer.create(this, R.raw.comidas_sound);
        mediaPlayerEmociones = MediaPlayer.create(this, R.raw.emociones_sound);
        mediaPlayerAcciones = MediaPlayer.create(this, R.raw.acciones_sound);




        btnGoToCrud = findViewById(R.id.btnGoToCrud);

        // Verificar si los archivos de sonido existen
        if (mediaPlayerEmociones == null) {
            Toast.makeText(this, "Error: Archivo de sonido para almuerzo no encontrado", Toast.LENGTH_SHORT).show();
        }

        if (mediaPlayerMeals == null) {
            Toast.makeText(this, "Error: Archivo de sonido para cena no encontrado", Toast.LENGTH_SHORT).show();
        }
        if (mediaPlayerAcciones == null) {
            Toast.makeText(this, "Error: Archivo de sonido para cena no encontrado", Toast.LENGTH_SHORT).show();
        }


        // Configurar botones para reproducir sonidos y cambiar de actividad
        btnMeals.setOnClickListener(view -> {
            if (mediaPlayerMeals != null) {
                playSound(mediaPlayerMeals);
                Toast.makeText(LobbyActivity.this, "Comidas seleccionadas", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LobbyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnEmociones.setOnClickListener(view -> {
            if (mediaPlayerEmociones != null) {
                playSound(mediaPlayerEmociones);
                Toast.makeText(LobbyActivity.this, "Emociones seleccionadas", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LobbyActivity.this, EmocionesActivity.class);
                startActivity(intent);
            }
        });

        btnAcciones.setOnClickListener(view -> {
            if (mediaPlayerAcciones != null) {
                playSound(mediaPlayerAcciones);
                Toast.makeText(LobbyActivity.this, "Acciones seleccionadas", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LobbyActivity.this, AccionesActivity.class);
                startActivity(intent);
            }
        });

        // Inicializar lista de personalizados y configurar RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        personalizadoList = new ArrayList<>();
        personalizadoAdapter = new PersonalizadoAdapter(this, personalizadoList);  // Instancia del adaptador
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(personalizadoAdapter);

        // Configurar Firebase para cargar datos
        databaseReference = FirebaseDatabase.getInstance().getReference("media");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalizadoList.clear();  // Limpia la lista antes de agregar nuevos datos

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String audioPath = snapshot.child("audio_path").getValue(String.class);
                    String imagePath = snapshot.child("image_path").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);

                    // Crear un objeto Personalizado con la URL de la imagen
                    Personalizado personalizado = new Personalizado(name, imagePath, audioPath);
                    personalizadoList.add(personalizado);  // Añade el elemento a la lista
                }

                personalizadoAdapter.notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LobbyActivity.this, "Error al cargar datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToCrud.setOnClickListener(view -> {
            Intent intent = new Intent(LobbyActivity.this, CrudActivity.class);
            startActivity(intent);
        });

        // Ir a la actividad Personalizados
        Button btnPersonalizados = findViewById(R.id.btnPersonalizados);
        btnPersonalizados.setOnClickListener(view -> {
            Intent intent = new Intent(LobbyActivity.this, PersonalizadosActivity.class);
            startActivity(intent);
        });

    }


    private void playSound(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer(mediaPlayerMeals);
        releaseMediaPlayer(mediaPlayerEmociones);
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
