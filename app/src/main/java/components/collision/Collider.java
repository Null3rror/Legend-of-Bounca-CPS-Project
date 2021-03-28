package components.collision;

import coreModule.Bounds;
import coreModule.GameObject;

public abstract class Collider {
    public GameObject gameObject;
    public Bounds bounds;
    public abstract boolean DetectCollision(Collider other);
}
