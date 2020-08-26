package com.kevinhomorales.anclaideas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Description extends AppCompatActivity {

    private Button stepOne;
    private Button stepTwo;
    private Button stepThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        stepOne = (Button) findViewById(R.id.button_step1);
        stepOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Dreamer.class);
                startActivity(in);
            }
        });

        stepTwo = (Button) findViewById(R.id.button_step2);
        stepTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Realistic.class);
                startActivity(in);
            }
        });

        stepThree = (Button) findViewById(R.id.button_step3);
        stepThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Critical.class);
                startActivity(in);
            }
        });
    }
}