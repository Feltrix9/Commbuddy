package com.example.prueba1;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalizadoRepository {

    private static PersonalizadoRepository instance;
    private List<Personalizado> personalizados;
    private DatabaseReference databaseReference;

    private PersonalizadoRepository() {
        personalizados = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("media");
    }

    public static PersonalizadoRepository getInstance() {
        if (instance == null) {
            instance = new PersonalizadoRepository();
        }
        return instance;
    }

    public void loadPersonalizados(final PersonalizadoLoadListener listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personalizados.clear(); // Limpiar la lista antes de cargar nuevos datos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nombre = snapshot.child("name").getValue(String.class);
                    String audioPath = snapshot.child("audio_path").getValue(String.class);
                    String imagePath = snapshot.child("image_path").getValue(String.class);

                    // Aquí puedes convertir audioPath e imagePath a los ID de recursos locales o manejar las URLs como necesites
                    Personalizado personalizado = new Personalizado(nombre, imagePath, audioPath);
                    personalizados.add(personalizado);
                }
                listener.onPersonalizadosLoaded(personalizados); // Notificar que los datos han sido cargados
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onError(databaseError.getMessage());
            }
        });
    }

    public List<Personalizado> getPersonalizados() {
        return new ArrayList<>(personalizados);
    }

    public interface PersonalizadoLoadListener {
        void onPersonalizadosLoaded(List<Personalizado> personalizados);
        void onError(String error);
    }
}

