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
    private List<MediaItem> personalizadoList;  // Cambiar a MediaItem
    private FirebaseStorage storage;
    private MediaPlayer mediaPlayer;

    public PersonalizadoAdapter(Context context, List<MediaItem> personalizadoList) {
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
        MediaItem personalizado = personalizadoList.get(position);  // Cambiar a MediaItem

        holder.textViewNombre.setText(personalizado.getName());  // Usar los getters de MediaItem

        // Cargar la imagen usando Glide
        Glide.with(context)
                .load(personalizado.getImagePath())  // Usar el getter correspondiente de MediaItem
                .into(holder.imageButtonPersonalizado);

        // Reproducir el sonido cuando se haga clic en el ImageButton
        holder.imageButtonPersonalizado.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            // Obtener la referencia al sonido en Firebase Storage
            StorageReference sonidoRef = storage.getReferenceFromUrl(personalizado.getAudioPath());

            // Descargar y reproducir el sonido
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



