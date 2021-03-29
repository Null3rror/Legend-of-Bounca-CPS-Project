package components;

import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Time;
import coreModule.Vector2;


public class RigidBody {
    private float mass;
    private Vector2 velocity;
    private Vector2 acceleration;
    private GameObject gameObject;
    private boolean isFalling;
    private float g;

    public RigidBody(float mass, GameObject gameObject) {
        this.mass = mass;
        this.velocity = Vector2.Zero();
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
        this.isFalling = true;
        this.g = Constants.g;
    }

    public void Update() {
        // sesnor got angle
        if(isFalling){
            acceleration.Set(0, g);
            SetVelocity();
            SetPosition();
        }else{
            System.out.println("33: jeddan hichi!!");
        }
    }

    private void SetVelocity(){
        Vector2 v0 = new Vector2(this.velocity);
        velocity.Set(acceleration.x * Time.DeltaTime() * 0.5f + v0.x, acceleration.y * Time.DeltaTime() * 0.5f + v0.y);
    }

    private void SetPosition(){
        Vector2 p0 = new Vector2(gameObject.transform.position);
        gameObject.transform.position.Set(velocity.x * Time.DeltaTime() + p0.x, velocity.y * Time.DeltaTime() + p0.y);
    }

}
