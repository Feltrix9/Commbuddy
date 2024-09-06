package com.example.prueba1;


import android.content.Context;
import android.util.Log;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public DatabaseHelper(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("media");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    // Insert a new record (equivalent to insertData)
    public void insertData(String name, String imagePath, String audioPath) {
        Map<String, Object> mediaData = new HashMap<>();
        mediaData.put("name", name);
        mediaData.put("image_path", imagePath);
        mediaData.put("audio_path", audioPath);
        databaseReference.push().setValue(mediaData);
    }

    // Retrieve all records (equivalent to getAllData)
    public void getAllData(DataChangeListener listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String imagePath = snapshot.child("image_path").getValue(String.class);
                    String audioPath = snapshot.child("audio_path").getValue(String.class);
                    listener.onDataChange(name, imagePath, audioPath);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public interface DataChangeListener {
        void onDataChange(String name, String imagePath, String audioPath);
    }

    // Update a record (equivalent to updateData)
    public void updateData(String key, String name, String imagePath, String audioPath) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("image_path", imagePath);
        updatedData.put("audio_path", audioPath);
        databaseReference.child(key).updateChildren(updatedData);
    }

    // Delete a record (equivalent to deleteData)
    public void deleteData(String key) {
        databaseReference.child(key).removeValue();
    }

    // Upload Image to Cloud Storage
    public void uploadImage(Uri imageUri, String imageName) {
        StorageReference imageRef = storageReference.child("images/" + imageName);

        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Image uploaded successfully
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();
                // You can update Realtime Database with download URL if needed
            }).addOnFailureListener(exception -> {
                // Handle errors getting download URL
                Log.e(TAG, "Error getting download URL", exception);
            });
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.e(TAG, "Image upload failed", exception);
        });
    }

    // Upload Audio to Cloud Storage
    public void uploadAudio(Uri audioUri, String audioName) {
        StorageReference audioRef = storageReference.child("audio/" + audioName);

        UploadTask uploadTask = audioRef.putFile(audioUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Audio uploaded successfully
            audioRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();
                // You can update Realtime Database with download URL if needed
            }).addOnFailureListener(exception -> {
                // Handle errors getting download URL
                Log.e(TAG, "Error getting download URL", exception);
            });
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.e(TAG, "Audio upload failed", exception);
        });
    }
}