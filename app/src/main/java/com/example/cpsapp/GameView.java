package com.example.cpsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.Collections;
import java.util.Random;

import coreModule.Constants;
import coreModule.GameObject;
import gameObjects.Ball;
import gameObjects.GameBorder;
import gameObjects.ThrowButton;


public class GameView extends SurfaceView implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private GameObject ball;
    private Paint paint;
    private GameObject border;
    private ThrowButton button;


    public GameView(Context context) {
        super(context);
        ball = new Ball(Constants.ballRadius,
                Constants.ballMass,
                GameBorder.GetScreenWidth()/2,GameBorder.GetScreenHeight()/2,
                Constants.ballTag,
                Collections.singletonList(Constants.borderTag));
        border = new GameBorder(Constants.borderTag, null);
        button = new ThrowButton(Constants.buttonTag, null);

        //TODO ADD partab moshak button
        paint = new Paint();
    }

    @Override
    public void run() {
        while (isRunning){
            //sensors
            Update();
            LateUpdate();
            Draw();
            Sleep();
        }
    }

    private void Update() {
        for (GameObject object:
             GameObject.gameObjects) {
            object.Update();
        }
    }

    private void LateUpdate() {
//        System.out.println(button.IsButtonPress(69, 69));
        // nothing

    }

    private void Draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE); // draw background g
            border.Render(canvas, paint);
            ball.Render(canvas, paint);
            button.Render(canvas, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void Sleep() {
        try {
            Thread.sleep(Constants.fixedDeltaTime); // fps = 60
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Resume() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void Pause() {
        try {
            isRunning = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public  static int RandomNumberGenerator(int min , int max){
        int random = new Random().nextInt(max - min + 1) + min;
       // System.out.println(random);
        return random;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(button.IsButtonPress(x, y)){
            if(ball.rigidBody.CanShoot())
                ball.rigidBody.Shoot();
        }
        return true;
    }
}
//
//-226.02258, 226.02258
//        -228.94202
//        -229.16179
