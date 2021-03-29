package com.example.cpsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import coreModule.GameObject;
import gameObjects.Ball;

public class GameView extends SurfaceView implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private GameObject ball;
    private Paint paint;


    public GameView(Context context) {
        super(context);
        ball = new Ball();
        ball.transform.position.Set(50, 50);
        ball.transform.position.Set(50, 50);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void run() {
        while (isRunning){
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
