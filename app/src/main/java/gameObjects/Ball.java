package gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.SpriteType;

import java.util.List;

import components.RigidBody;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Vector2;

public class Ball extends GameObject {
    private Sprite sprite;

    public Ball(float radius, float mass, int startPosX, int startPosY, String tag, List<String> tagsToCheckCollisionWith) {
        super(tag, tagsToCheckCollisionWith);
        this.transform = new Transform(startPosX, startPosY, radius * 2, radius * 2);

        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.rigidBody = new RigidBody(mass, this);
        this.sprite = new Sprite(this, SpriteType.Circle);
    }

    public boolean CanShoot(){
        return rigidBody.GetStaticStatus();
    }

    public void ScheduleShoot(){
        this.rigidBody.addForce = true;
    }

    @Override
    public void Update() {
//        System.out.println("Ball pos:" + transform.position);
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
