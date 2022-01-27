package com.example.smartphoneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonLogin;
    Button buttonAssociate;
    View.OnClickListener handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAssociate = (Button) findViewById(R.id.jumpAssociateButton);

        buttonAssociate.setOnClickListener(handler);


        buttonAssociate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AssociateIntent = new Intent(MainActivity.this, AssociateMobile.class);
                startActivity(AssociateIntent);
            }
        });
    }
}