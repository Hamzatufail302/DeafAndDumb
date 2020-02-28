package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Monitor extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST=3;
    private VideoView vv;
    private Uri videouri;
    private MediaController mediaController;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        vv=findViewById(R.id.vidupload);
        storageReference = FirebaseStorage.getInstance().getReference();
    }
    public void Upload(View view){
//        Intent i = new Intent();
//        i.setType("video/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(i,"select a video"),PICK_VIDEO_REQUEST);

        StorageReference riversRef = storageReference.child("images/rivers.jpg");

        riversRef.putFile(videouri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = StorageReference
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(Monitor.this,exception.toString(),Toast.LENGTH_SHORT)
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

}
