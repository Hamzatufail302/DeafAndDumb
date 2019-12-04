package com.example.elearningfordeafanddumbkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose_profile extends AppCompatActivity {
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_profile);
        button2= (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Openactivity();
            }
        });
    }

    public void  Openactivity(){
        Intent intent = new  Intent(this , choose_menu.class);
        startActivity(intent);

    }
}
