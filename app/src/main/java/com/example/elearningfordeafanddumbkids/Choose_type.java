package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choose_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type);
    }

    public void Fruit(View view) {
       startActivity(new Intent(this,Sixth.class));
    }
}
