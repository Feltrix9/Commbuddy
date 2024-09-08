package com.example.prueba1;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MediaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MediaAdapter mediaAdapter;
    private List<MediaAdapter.MediaItem> mediaList = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media); // Asegúrate de que este sea el nombre correcto del archivo XML

        recyclerView = findViewById(R.id.recyclerViewMedia); // Asegúrate de que este ID coincida con el del archivo XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mediaAdapter = new MediaAdapter(this, mediaList);
        recyclerView.setAdapter(mediaAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("media");
        loadMediaItems();
    }

    private void loadMediaItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mediaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String imagePath = snapshot.child("image_path").getValue(String.class);
                    String audioPath = snapshot.child("audio_path").getValue(String.class);

                    MediaAdapter.MediaItem mediaItem = new MediaAdapter.MediaItem(name, imagePath, audioPath);
                    mediaList.add(mediaItem);
                }
                mediaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }
}
