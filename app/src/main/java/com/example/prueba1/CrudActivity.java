package com.example.prueba1;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CrudActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 1;
    private static final int SELECT_AUDIO = 2;

    private DatabaseHelper db;
    private EditText editTextName;
    private ImageView imageViewSelected;
    private TextView textViewAudioSelected;
    private String selectedImagePath;
    private String selectedAudioPath;
    private LinearLayout linearLayoutButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        db = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        imageViewSelected = findViewById(R.id.imageViewSelected);
        textViewAudioSelected = findViewById(R.id.textViewAudioSelected);
        linearLayoutButtons = findViewById(R.id.linearLayoutButtons);

        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        Button buttonSelectAudio = findViewById(R.id.buttonSelectAudio);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        // Seleccionar Imagen
        buttonSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_IMAGE);
        });

        // Seleccionar Audio
        buttonSelectAudio.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_AUDIO);
        });

        // Agregar Objeto
        buttonAdd.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            if (name.isEmpty() || selectedImagePath == null || selectedAudioPath == null) {
                Toast.makeText(CrudActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get URIs from selectedImagePath and selectedAudioPath (assuming these are file paths)
            Uri imageUri = Uri.parse(selectedImagePath);
            Uri audioUri = Uri.parse(selectedAudioPath);

            db.uploadImage(imageUri, name + "_image");
            db.uploadAudio(audioUri, name + "_audio");

            // After uploads are complete (or you want to store references)
            db.insertData(name, "gs://commbuddy-ddcd7.appspot.com/images/" + name + "_image",
                    "gs://commbuddy-ddcd7.appspot.com/audio/" + name + "_audio");
            Toast.makeText(CrudActivity.this, "Objeto agregado", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            imageViewSelected.setImageResource(android.R.color.transparent);
            textViewAudioSelected.setText("");
            selectedImagePath = null;
            selectedAudioPath = null;
        });

        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar la actividad actual y volver a la anterior
                finish();
            }
        });

        // Cargar los botones existentes desde la base de datos
        loadExistingData();
    }

    private void loadExistingData() {
        db.getAllData(new DatabaseHelper.DataChangeListener() {
            @Override
            public void onDataChange(String name, String imagePath, String audioPath) {
                addButton(linearLayoutButtons, name, imagePath, audioPath);
            }
        });
    }

    private void addButton(LinearLayout linearLayoutButtons, String name, String imagePath, String audioPath) {
        Button newButton = new Button(this);
        newButton.setText(name);

        newButton.setOnClickListener(view -> {
            Toast.makeText(CrudActivity.this, "Nombre: " + name, Toast.LENGTH_SHORT).show();
        });

        linearLayoutButtons.addView(newButton);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedUri = data.getData();

            if (requestCode == SELECT_IMAGE) {
                selectedImagePath = selectedUri.toString();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedUri);
                    imageViewSelected.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_AUDIO) {
                selectedAudioPath = selectedUri.toString();
                textViewAudioSelected.setText("Audio seleccionado: " + selectedUri.getLastPathSegment());
            }
        }
    }
}