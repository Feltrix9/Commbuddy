package com.example.prueba1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private final Context context;
    private final List<MediaItem> mediaList;

    public MediaAdapter(Context context, List<MediaItem> mediaList) {
        this.context = context;
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        MediaItem mediaItem = mediaList.get(position);
        holder.bind(mediaItem);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    class MediaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textViewName;

        public MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.textViewName);
        }

        public void bind(MediaItem mediaItem) {
            textViewName.setText(mediaItem.getName());
            Picasso.get().load(Uri.parse(mediaItem.getImagePath())).into(imageView);
        }
    }

    public static class MediaItem {
        private final String name;
        private final String imagePath;
        private final String audioPath;

        public MediaItem(String name, String imagePath, String audioPath) {
            this.name = name;
            this.imagePath = imagePath;
            this.audioPath = audioPath;
        }

        public String getName() {
            return name;
        }

        public String getImagePath() {
            return imagePath;
        }

        public String getAudioPath() {
            return audioPath;
        }
    }
}
