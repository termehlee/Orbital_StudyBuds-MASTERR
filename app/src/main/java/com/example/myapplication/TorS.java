package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class TorS extends AppCompatActivity {
    private Button buttonT;
    private Button buttonS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_or_s);
        getSupportActionBar().setTitle("Choose Your Agenda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonT = findViewById(R.id.buttonT);
        buttonS = findViewById(R.id.buttonS);


        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTorT();
            }
        });
    }
    public void openTorT() {
        Intent intent = new Intent(this, TorT.class);
        startActivity(intent);
    }

}
