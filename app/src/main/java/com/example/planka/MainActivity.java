package com.example.planka;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import model.Graph;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Graph-object
        Graph g = new Graph(this.getAssets());



    }
}
