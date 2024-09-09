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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

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
    private String selectedObjectId; // Para identificar el objeto a actualizar o eliminar

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
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);

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

            // Reset fields and reload data
            resetFields();
            Toast.makeText(CrudActivity.this, "Objeto agregado", Toast.LENGTH_SHORT).show();
        });

        // Actualizar Objeto
        buttonUpdate.setOnClickListener(view -> {
            if (selectedObjectId == null) {
                Toast.makeText(CrudActivity.this, "No se ha seleccionado ningún objeto", Toast.LENGTH_SHORT).show();
                return;
            }

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

            // Update data
            db.updateData(selectedObjectId, name, "gs://commbuddy-ddcd7.appspot.com/images/" + name + "_image",
                    "gs://commbuddy-ddcd7.appspot.com/audio/" + name + "_audio");

            // Reset fields and reload data
            resetFields();
            Toast.makeText(CrudActivity.this, "Objeto actualizado", Toast.LENGTH_SHORT).show();
        });

        // Eliminar Objeto
        buttonDelete.setOnClickListener(view -> {
            if (selectedObjectId == null) {
                Toast.makeText(CrudActivity.this, "No se ha seleccionado ningún objeto", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener las rutas de los archivos (imagen y audio) asociados al objeto
            db.getData(selectedObjectId, new DatabaseHelper.DataChangeListener() {
                @Override
                public void onDataChange(String key, String name, String imagePath, String audioPath) {
                    // Eliminar los archivos de Firebase Storage
                    deleteFileFromFirebaseStorage(imagePath);
                    deleteFileFromFirebaseStorage(audioPath);

                    // Después de eliminar los archivos, eliminar los datos de la base de datos
                    db.deleteData(selectedObjectId);

                    // Reset fields and reload data
                    resetFields();
                    Toast.makeText(CrudActivity.this, "Objeto eliminado", Toast.LENGTH_SHORT).show();
                    resetFields();
                }
            });
        });

        //voy y vuelvo


        Button backButton = findViewById(R.id.btnVolver);
        backButton.setOnClickListener(v -> finish());

        // Cargar los botones existentes desde la base de datos
        loadExistingData();
    }

    private void loadExistingData() {
        // Limpiar los botones existentes antes de recargar
        linearLayoutButtons.removeAllViews();

        db.getAllData(new DatabaseHelper.DataChangeListener() {
            @Override
            public void onDataChange(String key, String name, String imagePath, String audioPath) {
                addButton(linearLayoutButtons, key, name, imagePath, audioPath);
            }
        });
    }

    private void addButton(LinearLayout linearLayoutButtons, String key, String name, String imagePath, String audioPath) {
        Button newButton = new Button(this);
        newButton.setText(name);

        newButton.setOnClickListener(view -> {
            Toast.makeText(CrudActivity.this, "Nombre: " + name, Toast.LENGTH_SHORT).show();
            editTextName.setText(name);
            selectedImagePath = imagePath;
            selectedAudioPath = audioPath;
            selectedObjectId = key; // Usar el key generado por Firebase como ID

            imageViewSelected.setImageURI(Uri.parse(selectedImagePath));
            textViewAudioSelected.setText("Audio seleccionado: " + Uri.parse(selectedAudioPath).getLastPathSegment());
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

    private void deleteFileFromFirebaseStorage(String fileUrl) {
        // Crear una referencia al archivo usando su URL
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileUrl);

        // Eliminar el archivo
        storageReference.delete().addOnSuccessListener(aVoid -> {
            // Archivo eliminado exitosamente
            Toast.makeText(CrudActivity.this, "Archivo eliminado", Toast.LENGTH_SHORT).show();
            resetFields();
        }).addOnFailureListener(exception -> {
            // Ocurrió un error al eliminar el archivo
            Toast.makeText(CrudActivity.this, "Error al eliminar archivo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            resetFields();
        });
    }

    private void resetFields() {
        // Limpiar los campos
        editTextName.setText("");
        imageViewSelected.setImageResource(android.R.color.transparent);
        textViewAudioSelected.setText("");
        selectedImagePath = null;
        selectedAudioPath = null;
        selectedObjectId = null; // Asegurarse de que no hay ningún objeto seleccionado
        linearLayoutButtons.removeAllViews(); // Eliminar todos los botones existentes
        loadExistingData(); // Volver a cargar los datos para mostrar el estado actualizado
    }
}
