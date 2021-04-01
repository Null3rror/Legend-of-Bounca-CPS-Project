package components;

import components.collision.Collider;
import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Time;
import coreModule.Vector2;


public class RigidBody {
    private final float mass;
    private Vector2 velocity;
    private Vector2 acceleration;
    private GameObject gameObject;
    private Vector2 force;
    private boolean notMoving;
    private float collidedEdgeAngle;
    private boolean hasCollided;


    private Vector2 lastVelocity;
    private Vector2 lastPosition;
    private Vector2 lastMax;



    public RigidBody(float mass, GameObject gameObject) {
        this.mass = mass;
        this.velocity = Vector2.Zero();
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
        this.force = Vector2.Zero();
        this.notMoving = false;
        this.hasCollided = false;
    }

    public void Update() {
        lastVelocity = new Vector2(velocity);
        lastPosition = new Vector2(gameObject.transform.position);
        lastMax = new Vector2(gameObject.collider.bounds.GetMax());


        float angle = gameObject.transform.rotation;
        float angleInRadian = (float)Math.toRadians(angle);
        float g = Constants.g;

        force.Set(mass * g * (float)Math.sin(angleInRadian),
                  mass * g * (float)Math.cos(angleInRadian));

//        HandleSlope();
        System.out.println("notMoving: " + IsNotMoving() + " velocity: " + velocity + " velocityMagnitude: " + velocity.Magnitude());
        UpdateAcceleration();
        UpdateObjectPosition();
        UpdateVelocity();


        collidedEdgeAngle = 90.0f;
        hasCollided = false;
    }

    private void HandleSlope() {
        if (gameObject.collider == null || !hasCollided) return;

        float g = Constants.g;
        notMoving = IsNotMoving();
        System.out.println("notMoving: " + IsNotMoving() + " velocity: " + velocity + " velocityMagnitude: " + velocity.Magnitude());

        if (!notMoving) {
            float thetaInRadian   = (float)Math.toRadians(collidedEdgeAngle);
            float fN              = mass * (float)Math.cos(thetaInRadian) * g;
            float fStaticFriction = fN * Constants.staticFrictionCoefficient;

            System.out.println("collidedAngle " + collidedEdgeAngle + " staticF: " + fStaticFriction + " force: " + force + " forcemag: " + force.Magnitude());
            if (CanSlideOnSlope(fStaticFriction, force)) {
                ApplyDynamicFriction(fN);
            }
            else {
                force.Set(0.0f, 0.0f);
            }
        }
        else {
            force.Set(0,0);
//            velocity.Set(0,0);
        }



    }

    private void ApplyDynamicFriction(float fN) {
        float fDynamicFriction = fN * Constants.kineticFrictionCoefficient;
//        Vector2 moveDirection = velocity.Normalize();
//        Vector2 friction = moveDirection.ScalarProduct(fDynamicFriction);
//        force.Set(
//                force.x + -Math.signum(velocity.x) * friction.x,
//                force.y + -Math.signum(velocity.y) * friction.y
//        );
    }


    private boolean CanSlideOnSlope(float fStaticFriction, Vector2 force) {
        return force.Magnitude() > fStaticFriction;
    }


    private boolean IsNotMoving()  { // checks if velocity is near zero
        return velocity.Magnitude() < Constants.velocityThreshold;
    }

    private void UpdateAcceleration() {
        acceleration.Set(
                (force.x / mass) * Constants.accelerationMultiplier,
                (force.y / mass) * Constants.accelerationMultiplier
        );
    }

    private void UpdateVelocity() {
        float deltaTime = Time.DeltaTime();
        System.out.print("Update Velocity: before: " + velocity);
        velocity.Set(
                acceleration.x * deltaTime + velocity.x,
                acceleration.y * deltaTime + velocity.y
        );
        System.out.println(" Update Velocity: after: " + velocity);
    }

    private void UpdateObjectPosition() {
        Vector2 prevPosition = gameObject.transform.position;
        float deltaTime = Time.DeltaTime();
        float squaredDeltaTime = (float)Math.pow(deltaTime, 2);
        float newPosX = 0.5f * acceleration.x * squaredDeltaTime + velocity.x * deltaTime + prevPosition.x;
        float newPosY = 0.5f * acceleration.y * squaredDeltaTime + velocity.y * deltaTime + prevPosition.y;
        gameObject.transform.position.Set(newPosX, newPosY);
    }

    public void HandleCollision(Collider other) {
        hasCollided = true;
        collidedEdgeAngle = gameObject.collider.bounds.GetCollidingEdgeAngle(other.bounds) % 360;
        UpdatePositionAfterCollision(other);
        Bounce(other);
        System.out.println(
                "ball pos: " + gameObject.transform.position + " ball center:" + gameObject.collider.bounds.center + "\n" +
                        "ball min: " + gameObject.collider.bounds.GetMin() + " ball max: " + gameObject.collider.bounds.GetMax() + "\n" +
                        "box pos: " + other.gameObject.transform.position + " box center:" + other.bounds.center + "\n" +
                        "box min: " + other.bounds.GetMin() + " box max: " + other.bounds.GetMax() + "\n"
        );
    }

    private void UpdatePositionAfterCollision(Collider other) {
        Vector2 min = gameObject.collider.bounds.GetMin();
        Vector2 max = gameObject.collider.bounds.GetMax();
        Vector2 otherMin = other.bounds.GetMin();
        Vector2 otherMax = other.bounds.GetMax();
        float h = 0;
        if (max.y > otherMax.y) { // bottom v.y
            h = Math.abs(lastMax.y - otherMax.y);
            velocity.y = Math.signum (lastVelocity.y) * (float) Math.sqrt(Math.pow(lastVelocity.y, 2) + 2 * acceleration.y * h);
            gameObject.transform.position.Set(gameObject.transform.position.x, otherMax.y - gameObject.collider.bounds.size.y / 2);
        }
        if (min.y < otherMin.y) { // top v.y
            h = otherMin.y - min.y;
//            velocity.y = Math.signum (velocity.y) * (float) Math.sqrt(Math.pow(velocity.y, 2) - 2 * acceleration.y * h);
            gameObject.transform.position.Set(gameObject.transform.position.x, otherMin.y + gameObject.collider.bounds.size.y / 2);
        }
        if (max.x > otherMax.x) { // right v.x
            h = max.x - otherMax.x;
//            velocity.x = Math.signum (velocity.x) * (float) Math.sqrt(Math.pow(velocity.x, 2) - 2 * acceleration.x * h);
            gameObject.transform.position.Set(otherMax.x - gameObject.collider.bounds.size.x / 2, gameObject.transform.position.y);
        }
        if (min.x < otherMin.x) { // left v.x
            h = otherMin.x - min.x;
//            velocity.x = Math.signum (velocity.x) * (float) Math.sqrt(Math.pow(velocity.x, 2) - 2 * acceleration.x * h);
            gameObject.transform.position.Set(otherMax.x + gameObject.collider.bounds.size.x / 2, gameObject.transform.position.y);
        }
        gameObject.collider.bounds.Update(gameObject.transform.position, gameObject.collider.bounds.size);
    }



    public void Bounce(Collider other) {
        Vector2 normal = gameObject.collider.bounds.CalculateHitPointNormal(other.bounds);
//        System.out.println("normal: " + normal);
        float dot = normal.DotProduct(velocity); // dot = -velocity.y
        System.out.print("Bounce: before: " + velocity);
        velocity.Set(
                velocity.x - 2 * dot * normal.x,
                velocity.y - 2 * dot * normal.y
        );
        System.out.println(" Bounce: after: " + velocity);
        ApplyEnergyLoss();
    }

    private void ApplyEnergyLoss() {
        Vector2 oldVelocity = new Vector2(velocity);
        velocity.Set(
                velocity.x * Constants.wastedEnergyCoefficient,
                velocity.y * Constants.wastedEnergyCoefficient
        );
        System.out.println("Applying energy loss: velocity before: " + oldVelocity + " after: " + velocity);

    }
}
