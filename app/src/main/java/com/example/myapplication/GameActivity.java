package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.SensorType;
import com.example.cpsapp.GameView;

import coreModule.Constants;
import coreModule.GameObject;

import android.hardware.SensorEventListener;
import coreModule.Constants.*;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class GameActivity extends Activity {

    private GameView gameView;
    private Sensor sensor;
    private SensorType sensorType;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private float timestamp;
    private float deltaT;
    private float d1, d2, d3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameObject.gameObjects.clear();
        System.out.println("Game Activity on create");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this);

        setContentView(gameView);

        Intent intent = getIntent();
        sensorType = (SensorType) intent.getExtras().get(("Sensor"));
//        SensorType sensorType = sensor.equals("Gyroscope") ? SensorType.Gyroscope : SensorType.Gravity;
        initSensor();
        //System.out.println(sensor);
//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        deltaT = (event.timestamp - timestamp) * Constants.ns2s;
//        if (timestamp != 0) {
//            if (sensorType.equals(SensorType.Gyroscope)) {
//                d1 = event.values[0];
//                d2 = event.values[1];
//                d3 = event.values[2];
//            }
//            else {
//                d1 = event.values[0];
//                d2 = event.values[1];
//                d3 = event.values[2];
//            }
//        }
//        timestamp = event.timestamp;
//    }

    protected void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorType.equals(SensorType.Gyroscope)) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

    }
}