package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.SensorType;
import com.example.cpsapp.GameView;

import coreModule.GameObject;
import sensor.GravitySensor;
import sensor.GyroscopeSensor;
import sensor.SensorBase;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private SensorManager sensorManager;
    private SensorBase sensorEventListener;
    private SensorType sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameObject.gameObjects.clear();
        System.out.println("Game Activity on create");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Intent intent = getIntent();
        sensorType = (SensorType) intent.getExtras().get(("Sensor"));
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