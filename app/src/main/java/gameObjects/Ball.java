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
        transform.position.Set(transform.position.x + 1 * Time.DeltaTime(), transform.position.y + 1 * Time.DeltaTime());
    }


    @Override
    public void Render(Canvas canvas, Paint paint) {
        sprite.Draw(canvas, paint);
    }
}
