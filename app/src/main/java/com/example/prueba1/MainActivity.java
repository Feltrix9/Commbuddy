package com.example.prueba1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {

    private ImageButton btnBreakfast, btnLunch, btnDinner;
    private MediaPlayer mediaPlayerBreakfast, mediaPlayerLunch, mediaPlayerDinner;
    private Button btnGoToCrud;
    private RecyclerView recyclerView;
    private PersonalizadoAdapter personalizadoAdapter;
    private List<Personalizado> personalizadoList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);

        // Cargar una imagen estática (opcional)
        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/commbuddy-ddcd7.appspot.com/o/images%2Fsirve%3F_image?alt=media";
        //Picasso.get()
        //        .load(imageUrl)
        //        .placeholder(R.drawable.placeholder_image) // Imagen que se muestra mientras carga
        //        .error(R.drawable.error_image) // Imagen en caso de error
       //         .into(imageView);

        // Inicializar el MediaPlayer con el sonido
        mediaPlayerBreakfast = MediaPlayer.create(this, R.raw.breakfast_sound);
        mediaPlayerLunch = MediaPlayer.create(this, R.raw.lunch_sound);
        mediaPlayerDinner = MediaPlayer.create(this, R.raw.dinner_sound);

        btnGoToCrud = findViewById(R.id.btnGoToCrud);

        // Verificar si los archivos de sonido existen
        if (mediaPlayerLunch == null) {
            Toast.makeText(this, "Error: Archivo de sonido para almuerzo no encontrado", Toast.LENGTH_SHORT).show();
        }

        if (mediaPlayerDinner == null) {
            Toast.makeText(this, "Error: Archivo de sonido para cena no encontrado", Toast.LENGTH_SHORT).show();
        }

        // Inicializar botones
        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);

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
                Toast.makeText(MainActivity.this, "Error al cargar datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración de botones y sonidos
        btnBreakfast.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Desayuno seleccionado", Toast.LENGTH_SHORT).show();
            playSound(mediaPlayerBreakfast);
            Intent intent = new Intent(MainActivity.this, BreakfastActivity.class);
            startActivity(intent);
        });

        btnLunch.setOnClickListener(view -> {
            if (mediaPlayerLunch != null) {
                playSound(mediaPlayerLunch);
                Toast.makeText(MainActivity.this, "Almuerzo seleccionado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LunchActivity.class);
                startActivity(intent);
            }
        });

        btnDinner.setOnClickListener(view -> {
            if (mediaPlayerDinner != null) {
                playSound(mediaPlayerDinner);
                Toast.makeText(MainActivity.this, "Cena seleccionada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DinnerActivity.class);
                startActivity(intent);
            }
        });

        btnGoToCrud.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CrudActivity.class);
            startActivity(intent);
        });

        // Ir a la actividad Personalizados
        Button btnPersonalizados = findViewById(R.id.btnPersonalizados);
        btnPersonalizados.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PersonalizadosActivity.class);
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
        releaseMediaPlayer(mediaPlayerBreakfast);
        releaseMediaPlayer(mediaPlayerLunch);
        releaseMediaPlayer(mediaPlayerDinner);
    }

    private void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

