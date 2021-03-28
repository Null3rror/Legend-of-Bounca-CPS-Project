package com.example.cpsapp;

import android.content.Context;
import android.view.SurfaceView;
import android.widget.ImageView;

import coreModule.GameObject;
import gameObjects.Ball;

public class GameView extends SurfaceView implements Runnable {
    private boolean isRunning;
    private Thread thread;
    private GameObject ball;


    public GameView(Context context, ImageView ballSprite) {
        super(context);
        ball = new Ball(ballSprite);

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

        ball.Draw();
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
