package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;

import com.example.SensorType;
import com.example.cpsapp.GameView;

import coreModule.GameObject;
import sensor.GravitySensor;
import sensor.GyroscopeSensor;
import sensor.SensorBase;

import android.hardware.SensorEventListener;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Sensor sensor;
    private SensorType sensorType;
    private SensorManager sensorManager;
    private SensorBase sensorEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameObject.gameObjects.clear();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);


        Intent intent = getIntent();
        sensorType = (SensorType) intent.getExtras().get(("Sensor"));


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        InstantiateSensor();
        

        gameView = new GameView(this, sensorEventListener);

        setContentView(gameView);


    }

    private void InstantiateSensor() {
        if (sensorType.equals(SensorType.Gyroscope)) {
            sensorEventListener = new GyroscopeSensor(sensorManager);
        }
        else if (sensorType.equals(SensorType.Gravity)) {
            sensorEventListener = new GravitySensor(sensorManager);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.Pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.Resume();
    }
    @Override
    public void onBackPressed() {
        onStop();
        Intent mainActivity = new Intent(GameActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

}