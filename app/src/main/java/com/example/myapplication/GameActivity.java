package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.SensorType;
import com.example.cpsapp.GameView;

import coreModule.GameObject;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;


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
        String sensor = intent.getStringExtra("Sensor");
        //System.out.println(sensor);
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