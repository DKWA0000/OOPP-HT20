package com.example.planka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class FilipActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        IncidentItemView testing = new IncidentItemView(this);
    }

    public void ToMain(View view) {
        Intent intent = new Intent(this, JaokActivity.class);
        startActivity(intent);
    }
}
