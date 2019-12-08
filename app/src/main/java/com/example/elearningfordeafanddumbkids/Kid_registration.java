package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static com.google.firebase.auth.FirebaseAuth.*;

public class Kid_registration extends AppCompatActivity {
    private EditText email, password, name,Age;
    private Button kidregister;
    private TextView userlogin;
    private FirebaseAuth firebaseAuth = getInstance();
    private ImageView userimage;
    String Name,Email,Password,age;
    private static int Pick_image=123;
    Uri ImagePath;
    private StorageReference storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if(requestCode==Pick_image && resultCode==RESULT_OK && data.getData() != null){
            ImagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImagePath);
                userimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_registration);
        setupUI();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Pick_image);
            }
        });


        kidregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                Toast.makeText(Kid_registration.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Kid_registration.this, login.class));
                            } else {
                                Toast.makeText(Kid_registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kid_registration.this, login.class));
            }
        });

    }

    private void setupUI() {
        email = (EditText) findViewById(R.id.kemail);
        password = (EditText) findViewById(R.id.kpassword);
        kidregister = (Button) findViewById(R.id.kidregister);
        userlogin = (TextView) findViewById(R.id.movetologin);
        name = (EditText) findViewById(R.id.kusername);
        Age=(EditText)findViewById(R.id.kage);
        userimage=(ImageView)findViewById(R.id.kidpic);

    }

    private Boolean validate() {
        Boolean result = false;
         Email = email.getText().toString();
         Password = password.getText().toString();
         Name = name.getText().toString();
         age=Age.getText().toString();
        if (Email.isEmpty() || Password.isEmpty() || Name.isEmpty()||age.isEmpty()) {
            Toast.makeText(this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference imagereferance= storageReference.child(firebaseAuth.getUid()).child("Images").child("profile pic");
        UploadTask uploadTask=imagereferance.putFile(ImagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             Toast.makeText(Kid_registration.this,"upload failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Kid_registration.this,"upload Success",Toast.LENGTH_SHORT).show();

            }
        });
        UserProfile userProfile = new UserProfile(age,Email,Name);
        myref.setValue(userProfile);
    }

}
