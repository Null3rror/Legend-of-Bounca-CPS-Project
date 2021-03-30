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
        this.velocity.Set(300, 300);
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
        this.isFalling = true;
        this.g = Constants.g;
    }

    public void Update() {
        // sensor got angle
        if (isFalling) {
            acceleration.Set(g, g);
            Vector2 v0 = new Vector2(this.velocity);
            Vector2 p0 = new Vector2(gameObject.transform.position);
//            System.out.println("--> " + v0.x + ",--> " + v0.y);
            velocity.Set(acceleration.x * Time.DeltaTime() + v0.x, acceleration.y * Time.DeltaTime() + v0.y);

            gameObject.transform.position.Set(Time.DeltaTime() * 0.5f * (velocity.x + v0.x) + p0.x, Time.DeltaTime() * 0.5f * (velocity.y + v0.y) + p0.y);  // p = t/2(v + v0) + p0 = t/2(gt + v0 + v0) + p0 = t/2(gt + 2v0) + p0 = 1/2gt^2 + v0t + p0
        }
        else {
            System.out.println("on ground");
        }
    }

    public void Bounce(Vector2 hitNormal) {
//        -2 * hitNormal * velocity + velocity
        Vector2 temp;
        temp = hitNormal.ScalarProduct(-2f);
        temp = velocity.DotProduct(temp);
        velocity = temp.Sum(velocity);
//        velocity.Set((float) ((-1) * Constants.wastedEnergyCoefficient * velocity.x), (float) ((-1) * Constants.wastedEnergyCoefficient * velocity.y));
    }
}
