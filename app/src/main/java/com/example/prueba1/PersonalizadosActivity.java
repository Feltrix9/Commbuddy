package com.example.prueba1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PersonalizadosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPersonalizados;
    private PersonalizadoAdapter personalizadoAdapter;
    private List<Personalizado> personalizadoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizados);

        // Inicializar el RecyclerView
        recyclerViewPersonalizados = findViewById(R.id.recyclerViewPersonalizados);
        recyclerViewPersonalizados.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista y el adaptador
        personalizadoList = new ArrayList<>();
        personalizadoAdapter = new PersonalizadoAdapter(this, personalizadoList);  // Usar PersonalizadoAdapter
        recyclerViewPersonalizados.setAdapter(personalizadoAdapter);

        // Cargar los elementos personalizados desde Firebase
        cargarDatosDesdeFirebase();

        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> finish());
    }

    private void cargarDatosDesdeFirebase() {
        PersonalizadoRepository.getInstance().loadPersonalizados(new PersonalizadoRepository.PersonalizadoLoadListener() {
            @Override
            public void onPersonalizadosLoaded(List<Personalizado> personalizados) {
                personalizadoList.clear();
                personalizadoList.addAll(personalizados);
                personalizadoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PersonalizadosActivity.this, "Error al cargar datos: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


