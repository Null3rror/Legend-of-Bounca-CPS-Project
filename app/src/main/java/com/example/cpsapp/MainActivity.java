package com.example.cpsapp;
//
import androidx.appcompat.app.AppCompatActivity;
//
import android.os.Bundle;
//
import com.example.myapplication.R;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}


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

        ballSprite = findViewById(R.id.ball);
        gameView = new GameView(this, ballSprite);

//        setContentView(gameView);
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
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            ballY -= 20.0f;
//        }
//        ballSprite.setY(ballY);
        return super.onTouchEvent(event);
    }
}