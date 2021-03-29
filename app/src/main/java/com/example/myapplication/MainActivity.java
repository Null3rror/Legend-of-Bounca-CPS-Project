package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.cpsapp.GameView;

public class MainActivity extends AppCompatActivity {

    private ImageView ballSprite;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, GameActivity.class));
    }



//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        if (event.getAction() == MotionEvent.ACTION_DOWN) {
////            ballY -= 20.0f;
////        }
////        ballSprite.setY(ballY);
//        return super.onTouchEvent(event);
//    }
}