package components.collision;

import coreModule.Bounds;
import coreModule.GameObject;
import coreModule.Vector2;


public class BoxCollider extends Collider {
    public BoxCollider(GameObject gameObject, Vector2 center, Vector2 size) {
        this.gameObject = gameObject;
        this.bounds = new Bounds(center, size);
    }



    @Override
    public boolean DetectCollision(Collider other) {
        return bounds.Intersects(other.bounds);
    }
}
