package com.example.cpsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import java.util.Collections;

import coreModule.Constants;
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
        ball = new Ball(Constants.ballRadius,
                Constants.ballMass,
                500,
                50,
                Constants.ballTag,
                Collections.singletonList(Constants.borderTag));
        border = new GameBorder(Constants.borderTag, null);
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
        ball.Update();
        border.Update();
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

}
