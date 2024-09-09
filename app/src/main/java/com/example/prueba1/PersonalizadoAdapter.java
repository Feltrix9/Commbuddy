package com.example.prueba1;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class PersonalizadoAdapter extends RecyclerView.Adapter<PersonalizadoAdapter.PersonalizadoViewHolder> {

    private Context context;
    private List<Personalizado> personalizadoList;
    private FirebaseStorage storage;
    private MediaPlayer mediaPlayer;

    public PersonalizadoAdapter(Context context, List<Personalizado> personalizadoList) {
        this.context = context;
        this.personalizadoList = personalizadoList;
        this.storage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public PersonalizadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personalizado, parent, false);
        return new PersonalizadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalizadoViewHolder holder, int position) {
        Personalizado personalizado = personalizadoList.get(position);

        holder.textViewNombre.setText(personalizado.getNombre());

        // Verificar y cargar la imagen usando la URL fija según el nombre
        String imageUrl = getImageUrlForName(personalizado.getNombre());
        if (imageUrl != null) {
            ImageLoader.loadImage(imageUrl, holder.imageButtonPersonalizado);
        } else {
            holder.imageButtonPersonalizado.setImageResource(R.drawable.placeholder_image); // Imagen por defecto
        }

        holder.imageButtonPersonalizado.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }


            StorageReference sonidoRef = storage.getReferenceFromUrl(personalizado.getAudioPath());

            sonidoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(uri.toString());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    Log.e("Firebase", "Error al reproducir el sonido", e);
                }
            }).addOnFailureListener(e -> {
                Log.e("Firebase", "Error al cargar el sonido", e);
            });
        });
    }

    private String getImageUrlForName(String name) {
        if ("sirve?".equals(name)) {
            return context.getString(R.string.url_sirve);
        } else if ("lenny".equals(name)) {
            return context.getString(R.string.url_lenny);
        } else if ("Voz Victor".equals(name)) {
            return context.getString(R.string.url_vozVictor);
        }
        // else if ("nombre_var".equals(name)) {
        //return context.getString(R.string.url_nombre_var);
        //}
        return null; // Si no hay una URL definida para el nombre
    }

    @Override
    public int getItemCount() {
        return personalizadoList.size();
    }

    public static class PersonalizadoViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButtonPersonalizado;
        TextView textViewNombre;

        public PersonalizadoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButtonPersonalizado = itemView.findViewById(R.id.imageButtonPersonalizado);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
        }
    }
}