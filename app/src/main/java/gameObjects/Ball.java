package gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.SpriteType;

import java.util.List;

import components.RigidBody;
import components.SensorReader;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Vector2;
import coreModule.Vector4;

public class Ball extends GameObject {
    private Sprite sprite;
    private final SensorReader sensorReader;
    public Ball(float radius, float mass, int startPosX, int startPosY, String tag, List<String> tagsToCheckCollisionWith) {
        super(tag, tagsToCheckCollisionWith);
        this.transform = new Transform(startPosX, startPosY, radius * 2, radius * 2);

        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.rigidBody = new RigidBody(mass, this);
        this.sprite = new Sprite(this, SpriteType.Circle);
        this.sensorReader = new SensorReader();
    }

    public boolean CanShoot(){
        return rigidBody.GetStaticStatus();
    }

    public void ScheduleShoot(){
        this.rigidBody.addForce = true;
    }

    @Override
    public void Update() {
        Vector4 angles = sensorReader.GetAngles();
        System.out.println("angles " + Math.toDegrees(angles.x) + ", " + Math.toDegrees(angles.y) + ", " + Math.toDegrees(angles.z));
        Vector4 rawData = sensorReader.GetRawData();
        System.out.println("raw data :" + rawData.x + ", " + rawData.y + ", " + rawData.z);
        transform.rotation = (float) Math.toDegrees(angles.z);
//        System.out.println("Ball pos:" + transform.position);
    }

    @Override
    public void LateUpdate() {
        collider.Update();
        rigidBody.Update();
    }

    @Override
    public void Render(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        sprite.Draw(canvas, paint);
        Vector2 dir = rigidBody.GetVelocity().Normalize();
        canvas.drawLine(transform.position.x, transform.position.y,
                transform.position.x +  dir.x * 100,
                transform.position.y +  dir.y * 100, paint);
    }


    @Override
    public void OnCollisionEnter(Collider other) {
//
    }


}
