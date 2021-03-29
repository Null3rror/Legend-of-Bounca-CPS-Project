package gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.example.SpriteType;

import components.RigidBody;
import components.Sprite;
import components.Transform;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.GameObject;
import coreModule.Time;
import coreModule.Vector2;

public class Ball extends GameObject {
    private Collider collider;
    private RigidBody rigidBody;
    private Sprite sprite;

    public Ball() {
        this.transform = new Transform();
        this.transform.Reset();
        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.rigidBody = new RigidBody(0.01f, this);
        this.sprite = new Sprite(this, SpriteType.Circle);
    }

    @Override
    public void Update() {

        //sensor interface called!!!
        // fall ball
        rigidBody.Update();
        Vector2 position = transform.position;
        transform.position.Set(position.x + 250 * Time.DeltaTime(), position.y + 250 * Time.DeltaTime());
    }


    @Override
    public void Render(Canvas canvas, Paint paint) {
        sprite.Draw(canvas, paint);
    }
}
