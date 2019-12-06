package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Kid_registration extends AppCompatActivity {
    private EditText name,age,email,password;
    private Button register;
    private TextView allreadylogin;
    private FirebaseAuth firebaseAuth;
    private ImageView profilepicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_registration);
        SetupUI();
    }

    private void SetupUI(){
        name=(EditText)findViewById(R.id.kusername);
        age=(EditText)findViewById(R.id.kage);
        email=(EditText)findViewById(R.id.kemail);
        password=(EditText)findViewById(R.id.kpassword);
        profilepicture=(ImageView)findViewById(R.id.kidpic);

    }
    private  void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
    }
}
