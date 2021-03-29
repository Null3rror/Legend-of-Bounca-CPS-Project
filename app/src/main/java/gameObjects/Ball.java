package gameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import com.example.SpriteType;

import java.util.List;

import components.RigidBody;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.Bounds;
import coreModule.GameObject;
import coreModule.Time;
import coreModule.Vector2;

public class Ball extends GameObject {
    private Sprite sprite;

    public Ball(float radius, float mass, int startPosX, int startPosY, String tag, List<String> tagsToCheckCollisionWith) {
        super(tag, tagsToCheckCollisionWith);
        this.transform = new Transform(startPosX, startPosY, radius, radius);

        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.rigidBody = new RigidBody(mass, this);
        this.sprite = new Sprite(this, SpriteType.Circle);
    }

    @Override
    public void Update() {
        collider.Update();
        rigidBody.Update();
    }


    @Override
    public void Render(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        sprite.Draw(canvas, paint);
    }




    @Override
    public void OnCollisionEnter(Collider other) {
        Vector2 hitNormal = other.bounds.CalculateHitPointNormal(collider.bounds);
        rigidBody.Bounce(hitNormal);
    }



}
