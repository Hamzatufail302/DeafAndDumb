package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Monitor extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST=3;
    private VideoView vv;
    Uri ImagePath;
    private static int Pick_image = 123;
    private ImageView extraimage;
    public MediaController mediaController;
    private StorageReference storageReference;
    Uri videouri;
//private  String videoname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        extraimage=findViewById(R.id.chooseimage);
        vv=findViewById(R.id.vidupload);
        storageReference = FirebaseStorage.getInstance().getReference();

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController=new MediaController(Monitor.this);
                        vv.setMediaController(mediaController);
                        mediaController.setAnchorView(vv);
                    }
                });
            }
        });

        vv.start();
    }
    public void Upload(View view){

        sendUserData();
        if(videouri!=null){
            StorageReference riversRef = storageReference.child("/test/test.mp4");
            UploadTask uploadTask=riversRef.putFile(videouri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Monitor.this, "Upload Failed :"+e, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Monitor.this, "Upload Sucesfull", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this,"Video is empty ",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if()
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            videouri = data.getData();
            vv.setVideoURI(videouri);
            Toast.makeText(this, "Video is set", Toast.LENGTH_SHORT).show();

        }
        if (requestCode == Pick_image && resultCode == RESULT_OK && data.getData() != null) {
            ImagePath = data.getData();

        }


    }


    public void Videoup(View view) {
        Intent i = new Intent();
        i.setType("video/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"select a video"),PICK_VIDEO_REQUEST);
    }

    public void ImageUpload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Pick_image);
    }



    public void uploadimage(View view){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImagePath);
            extraimage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendUserData() {
        StorageReference imagereferance = storageReference.child("images").child("fruits").child("apple");
        UploadTask uploadTask = imagereferance.putFile(ImagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Monitor.this, "upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Monitor.this, "upload Success", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
