package gameObjects;

import android.widget.ImageView;

import components.RigidBody;
import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.GameObject;
import coreModule.Time;

public class Ball extends GameObject {
    private Collider collider;
    private RigidBody rigidBody;
    private ImageView sprite;

    public Ball(ImageView ballSprite) {
        this.transform.Reset();
        this.collider = new BoxCollider(this, transform.position, transform.size);
        this.rigidBody = new RigidBody(0.01f, this);
        this.sprite = ballSprite;
    }

    @Override
    public void Update() {
        transform.position.Set(transform.position.x + 1 * Time.DeltaTime(), transform.position.y + 1 * Time.DeltaTime());
    }

    @Override
    public void Draw() {
        sprite.setX(transform.position.x);
        sprite.setY(transform.position.y);
    }
}
