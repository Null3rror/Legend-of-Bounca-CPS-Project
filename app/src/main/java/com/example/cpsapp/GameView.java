package com.example.cpsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import coreModule.GameObject;
import gameObjects.Ball;
import gameObjects.GameBorder;

public class GameView extends SurfaceView implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private GameObject ball;
    private Paint paint;
    private GameObject border;


    public GameView(Context context) {
        super(context);
        ball = new Ball();
        ball.transform.position.Set(50, 50);
        ball.transform.position.Set(50, 50);
        paint = new Paint();


        border = new GameBorder();
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
        ball.Update();
    }

    private void LateUpdate() {
        // nothing
    }

    private void Draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE); // draw background g
            border.Render(canvas, paint);
            ball.Render(canvas, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void Sleep() {
        try {
            Thread.sleep(16); // fps = 60
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

}
