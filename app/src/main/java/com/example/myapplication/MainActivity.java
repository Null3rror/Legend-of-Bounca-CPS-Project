package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.cpsapp.GameView;
import com.example.SensorType;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Main Activity on create");

        Button btnGravity = (Button) findViewById(R.id.btn_Gravity);
        Button btnGyroscope = (Button) findViewById(R.id.btn_Gyroscope);

        btnGravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gravity = new Intent(MainActivity.this, GameActivity.class);
                gravity.putExtra("Sensor", SensorType.Gravity);
                startActivity(gravity);
            }
        });
        btnGyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gyroscope = new Intent(MainActivity.this, GameActivity.class);
                gyroscope.putExtra("Sensor", SensorType.Gyroscope);
                startActivity(gyroscope);
            }
        });
    }


}