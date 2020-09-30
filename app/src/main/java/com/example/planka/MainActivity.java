package com.example.planka;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import model.Network;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Graph-object
        Network g = new Network(this.getAssets());



    }
}
