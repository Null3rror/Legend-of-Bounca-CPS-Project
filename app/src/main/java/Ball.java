import components.collision.BoxCollider;
import components.collision.Collider;
import coreModule.GameObject;

public class Ball extends GameObject {
    public Collider collider;

    public Ball() {
        transform.Reset();
        collider = new BoxCollider(this);
    }
}
