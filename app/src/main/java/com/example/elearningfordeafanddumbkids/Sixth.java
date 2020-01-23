package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Sixth extends AppCompatActivity {

    private DatabaseReference databaseReference;
    StorageReference reference;
    private List myVideos;
    private VideoView videoView;
    Uri videouri;

    int index=0;
    String [] vid_names={"Apple","Banana","Mango"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        myVideos=new ArrayList();
        databaseReference= FirebaseDatabase.getInstance().getReference("videos");
        videoView=(VideoView)findViewById(R.id.my_vid);

        multiple(index);

    }

    public void multiple(int index){

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
   //     reference=storageReference.child("/videos/Apple.mp4");

        reference=storageReference.child("/videos/"+vid_names[index]+".mp4");


        try {
            final File localFile=File.createTempFile(vid_names[index],"mp4");
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    videoView.setVideoURI(Uri.fromFile(localFile));
                    videoView.start();

                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sixth.this, "Failed "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Next(View view) {
        index++;
        if(index>vid_names.length-1){
            index=0;
        }
        multiple(index);
    }
}
