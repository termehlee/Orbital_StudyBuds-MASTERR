package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TorT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tor_t);
        getSupportActionBar().setTitle("Role");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
