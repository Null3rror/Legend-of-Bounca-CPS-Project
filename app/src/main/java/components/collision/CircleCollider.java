package components.collision;

import coreModule.Bounds;
import coreModule.GameObject;
import coreModule.Vector2;

public class CircleCollider extends Collider {

    public float radius;
    public CircleCollider(GameObject gameObject, Vector2 center, float radius) {
        this.gameObject = gameObject;
        float diameter = radius * 2;
        this.bounds = new Bounds(center, new Vector2(diameter, diameter));
        this.radius = radius;
    }

    @Override
    public boolean DetectCollision(Collider other) {
        Vector2 distance = new Vector2(bounds.center.x - other.bounds.center.x, bounds.center.y - other.bounds.center.y);
        float distanceMagnitude = distance.Magnitude();

        return  distanceMagnitude < (radius + ((CircleCollider)other).radius);
    }
}
