package com.example.elearningfordeafanddumbkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Kid_login extends AppCompatActivity {
    private TextView textView1;
    private FirebaseAuth firebaseAuth;
    private Button button;
    private EditText name,password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_login);
        textView1 = (TextView) findViewById(R.id.kidregister);
        firebaseAuth=firebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        button=(Button)findViewById(R.id.loginkid);
        name=(EditText)findViewById(R.id.kidusername);
        password=(EditText)findViewById(R.id.kidpassword);

        FirebaseAuth user=FirebaseAuth.getInstance();


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Kid_login.this, Kid_registration.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(name.getText().toString(),password.getText().toString());
            }

        });



    }

    private void validate(String username, String userpass) {
        progressDialog.setMessage("please wait");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(username,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressDialog.dismiss();
                    Toast.makeText(Kid_login.this,"login Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Kid_login.this,choose_menu.class));
                }
                else{
                    Toast.makeText(Kid_login.this,"login failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}