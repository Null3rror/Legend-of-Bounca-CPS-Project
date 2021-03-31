package com.example.cpsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEventListener;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Random;

import coreModule.Constants;
import coreModule.GameObject;
import gameObjects.Ball;
import gameObjects.GameBorder;
import sensor.SensorBase;


public class GameView extends SurfaceView implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private GameObject ball;
    private Paint paint;
    private GameObject border;
    private SensorBase sensorEventListener;

    public GameView(Context context, SensorBase sensorEventListener) {
        super(context);
        this.sensorEventListener = sensorEventListener;
        ball = new Ball(Constants.ballRadius,
                Constants.ballMass,
                (GameBorder.GetScreenWidth() / 14 ) + RandomNumberGenerator(20 , (int)(GameBorder.GetScreenWidth() * 0.75)),
                (GameBorder.GetScreenHeight() / 7 ) + RandomNumberGenerator(20 , (int)(GameBorder.GetScreenHeight() * 0.75)),
                Constants.ballTag,
                Collections.singletonList(Constants.borderTag));
        border = new GameBorder(Constants.borderTag, null);
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
        System.out.println(sensorEventListener.GetData());
        for (GameObject object:
             GameObject.gameObjects) {
            object.Update();
        }
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
    public  static int RandomNumberGenerator(int min , int max){
        int random = new Random().nextInt(max - min + 1) + min;
       // System.out.println(random);
        return random;
    }

}
