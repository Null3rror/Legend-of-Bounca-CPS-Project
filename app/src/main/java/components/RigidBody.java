package components;

import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Time;
import coreModule.Vector2;
import com.example.cpsapp.GameView;


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
        this.velocity.Set(0, GameView.RandomNumberGenerator(100 , 250));
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
        this.isFalling = true;
        this.g = Constants.g;
        this.acceleration.Set(0, g);
    }

    public void Update() {
        // sensor got angle
       // System.out.println("--> " + velocity.x + ",--> " + velocity.y + " --> " + isFalling);
        if (isFalling) {
            Vector2 v0 = new Vector2(this.velocity);
            Vector2 p0 = new Vector2(gameObject.transform.position);

            velocity.Set(v0.x, acceleration.y * Time.DeltaTime() + v0.y);
            gameObject.transform.position.Set(Time.DeltaTime() * v0.x + p0.x, Time.DeltaTime() * 0.5f * (velocity.y + v0.y) + p0.y);  // p = t/2(v + v0) + p0 = t/2(gt + v0 + v0) + p0 = t/2(gt + 2v0) + p0 = 1/2gt^2 + v0t + p0
        }
        else { //TODO sath shib dar
            System.out.println("on ground");
        }
    }

    public void Bounce(Vector2 hitNormal) {
//        System.out.println(hitNormal);
//        -2 * hitNormal * velocity + velocity
        Vector2 temp;
        temp = hitNormal.ScalarProduct(-2f);
        temp = velocity.DotProduct(temp);
        velocity = temp.Sum(velocity);
        velocity.Set( Constants.wastedEnergyCoefficient * velocity.x,  Constants.wastedEnergyCoefficient * velocity.y);

//        CheckFallingStatus(); //TODO check hitNormal and fallingstatus
    }

    private void CheckFallingStatus() {
        if(Math.abs(velocity.y) < Constants.velocityThreshold )
            isFalling = false;
    }
}
