package components;

import coreModule.GameObject;
import coreModule.Vector2;

public class RigidBody {
    private float mass;
    private Vector2 velocity;
    private Vector2 acceleration;
    private GameObject gameObject;

    public RigidBody(float mass, GameObject gameObject) {
        this.mass = mass;
        this.velocity = Vector2.Zero();
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
    }


}
