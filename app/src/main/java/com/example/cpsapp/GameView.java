package com.example.cpsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Random;

import coreModule.Constants;
import coreModule.GameObject;
import gameObjects.Ball;
import gameObjects.GameBorder;
import gameObjects.SensorGameObject;
import gameObjects.ThrowButton;
import sensor.SensorBase;


public class GameView extends SurfaceView implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private Ball ball;
    private Paint paint;
    private GameObject border;
    private ThrowButton button;
    private SensorGameObject sensorGameObject;

    public GameView(Context context, SensorBase sensor) {
        super(context);
        sensorGameObject = new SensorGameObject(Constants.sensorTag, null, sensor);
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
//        sensorEventListener.GetData();
//        System.out.println(sensorEventListener.GetData());
        for (GameObject object:
             GameObject.gameObjects) {
            object.Update();
        }
    }

    private void LateUpdate() {
        for (GameObject object:
                GameObject.gameObjects) {
            object.LateUpdate();
        }

    }

    private void Draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.WHITE); // draw background g
            border.Render(canvas, paint);
            ball.Render(canvas, paint);
            button.Render(canvas, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(45);
            canvas.drawText(ball.GetAngle(), 0f, GameBorder.GetScreenHeight() - 100f, paint);
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
        return random;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(button.IsButtonPress(x, y)){
            if(ball.CanShoot())
                ball.ScheduleShoot();
        }
        return true;
    }
}
