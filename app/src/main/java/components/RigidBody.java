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

    public RigidBody(float mass, GameObject gameObject) {
        this.mass = mass;
        this.velocity = Vector2.Zero();
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
        this.force = Vector2.Zero();
        this.notMoving = false;
        this.hasCollided = false;
    }

    public void Update() { // TODO: to private
        float angle = gameObject.transform.rotation;
        float angleInRadian = (float)Math.toRadians(angle);
        float g = Constants.g;

        force.Set(mass * g * (float)Math.sin(angleInRadian),
                  mass * g * (float)Math.cos(angleInRadian));

        HandleSlope();

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
        System.out.println("notMoving: " + notMoving + " velocity: " + velocity + " velocityMagnitude: " + velocity.Magnitude());

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
        Vector2 moveDirection = velocity.Normalize();
        Vector2 friction = moveDirection.ScalarProduct(fDynamicFriction);
        force.Set(
                force.x + -Math.signum(velocity.x) * friction.x,
                force.y + -Math.signum(velocity.y) * friction.y
        );
    }


    private boolean CanSlideOnSlope(float fStaticFriction, Vector2 force) {
        return force.Magnitude() > fStaticFriction;
    }


    private boolean IsNotMoving()  { // checks if velocity is near zero
        return velocity.Magnitude() < Constants.velocityThreshold;
    }

    private void UpdateAcceleration() {
        acceleration.Set(
                force.x / mass * Constants.accelerationMultiplier,
                force.y / mass * Constants.accelerationMultiplier
        );
    }

    private void UpdateVelocity() {
        float deltaTime = Time.DeltaTime();
        velocity.Set(
                acceleration.x * deltaTime + velocity.x,
                acceleration.y * deltaTime + velocity.y
        );
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
        Bounce(other);
        UpdatePositionAfterCollision(other);
//        System.out.println(
//                "ball pos: " + gameObject.transform.position + " ball center:" + gameObject.collider.bounds.center + "\n" +
//                        "ball min: " + gameObject.collider.bounds.GetMin() + " ball max: " + gameObject.collider.bounds.GetMax() + "\n" +
//                        "box pos: " + other.gameObject.transform.position + " box center:" + other.bounds.center + "\n" +
//                        "box min: " + other.bounds.GetMin() + " box max: " + other.bounds.GetMax() + "\n"
//        );
    }

    private void UpdatePositionAfterCollision(Collider other) {
        Vector2 min = gameObject.collider.bounds.GetMin();
        Vector2 max = gameObject.collider.bounds.GetMax();
        Vector2 otherMin = other.bounds.GetMin();
        Vector2 otherMax = other.bounds.GetMax();
        if (max.y > otherMax.y) {
            gameObject.transform.position.Set(gameObject.transform.position.x, otherMax.y - gameObject.collider.bounds.size.y / 2);
        }
        if (min.y < otherMin.y) {
            gameObject.transform.position.Set(gameObject.transform.position.x, otherMin.y + gameObject.collider.bounds.size.y / 2);
        }
        if (max.x > otherMax.x) {
            gameObject.transform.position.Set(otherMax.x - gameObject.collider.bounds.size.x / 2, gameObject.transform.position.y);
        }
        if (min.y < otherMin.y) {
            gameObject.transform.position.Set(otherMax.x + gameObject.collider.bounds.size.x / 2, gameObject.transform.position.y);
        }
        gameObject.collider.bounds.Update(gameObject.transform.position, gameObject.collider.bounds.size);
    }


//    public void Update() {
//        Vector2 v0 = new Vector2(this.velocity);
//        Vector2 p0 = new Vector2(gameObject.transform.position);
//        // sensor got angle
////        System.out.println("--> " + velocity.x + ",--> " + velocity.y + " --> " + isFalling);
//        angle = Math.toRadians(60);
//        this.acceleration.Set(g * (float)Math.sin(Math.toRadians(angle)), g * (float)Math.cos(Math.toRadians(angle)));
//        if (isFalling) {
//
//            velocity.Set(acceleration.x * Time.DeltaTime() + v0.x, acceleration.y * Time.DeltaTime() + v0.y);
//            gameObject.transform.position.Set(Time.DeltaTime() * 0.5f * (velocity.x + v0.x) + p0.x, Time.DeltaTime() * 0.5f * (velocity.y + v0.y) + p0.y);  // p = t/2(v + v0) + p0 = t/2(gt + v0 + v0) + p0 = t/2(gt + 2v0) + p0 = 1/2gt^2 + v0t + p0
//        }
//        else {
//            if (Constants.staticFrictionCoefficient * Math.cos(angle) < Math.sin(angle)){
//                acceleration.Set((float) ( g * (Math.sin(angle) - Constants.kineticFrictionCoefficient * Math.cos(angle))) , 0);
//                velocity.Set(acceleration.x * Time.DeltaTime() + velocity.x  , 0);
//                gameObject.transform.position.Set(Time.DeltaTime() * 0.5f * (velocity.x + v0.x) + p0.x , p0.y) ;
//            }
//            else {
//                this.velocity = Vector2.Zero();
//                this.acceleration = Vector2.Zero();
//            }
//        }
//    }

    public void Bounce(Collider other) {
        Vector2 normal = gameObject.collider.bounds.CalculateHitPointNormal(other.bounds);
//        System.out.println("normal: " + normal);
        float dot = normal.DotProduct(velocity);
        velocity.Set(
                velocity.x - 2 * dot * normal.x,
                velocity.y - 2 * dot * normal.y
        );
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

//    private void CheckBallMovementStatus(Vector2 hitNormal) { // (0, -1)
//        if(Math.abs(velocity.y) < Constants.velocityThreshold && IsFloorNormal(hitNormal))
//            isFalling = false;
//    }

//    private boolean IsFloorNormal(Vector2 hitNormal){
//        return true;
//    }
}
