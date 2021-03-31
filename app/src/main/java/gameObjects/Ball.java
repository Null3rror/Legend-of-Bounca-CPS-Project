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
        Vector2 hasHitHorOrVer = other.bounds.HasHitHorOrVer(collider.bounds);
        Vector2 hitNormal = other.bounds.CalculateHitPointNormal(collider.bounds);
        rigidBody.Bounce(hasHitHorOrVer);

        if (hitNormal.y == -1 ){ //bottom
            Vector2 t2 = other.bounds.GetMax();
            transform.position.y = t2.y - Constants.borderThickness;
            System.out.println(String.format("bottom t2: %f y: %f"  , t2.y , transform.position.y));
        }
        else if (hitNormal.y == 1 ){ //top
            Vector2 t2 = other.bounds.GetMin();
            transform.position.y = t2.y + Constants.borderThickness;
            System.out.println(String.format("top t2: %f y: %f"  , t2.y , transform.position.y));
        }
        if (hitNormal.x == -1 ){ //right
            Vector2 t2 = other.bounds.GetMax();
            transform.position.x = t2.x - Constants.borderThickness;
            System.out.println(String.format("right t2: %f y: %f"  , t2.x , transform.position.y));
        }
        else if (hitNormal.x == 1 ){ //left
            Vector2 t2 = other.bounds.GetMin();
            transform.position.x = t2.x + Constants.borderThickness;
            System.out.println(String.format("left t2: %f y: %f"  , t2.x , transform.position.y));
        }

    }



}
