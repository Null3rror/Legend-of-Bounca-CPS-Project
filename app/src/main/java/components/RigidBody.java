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
    private Vector2 lastMin;
    private boolean isOnSlope;


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
        lastMin = new Vector2(gameObject.collider.bounds.GetMin());

        float angle = gameObject.transform.rotation;
        float angleInRadian = (float)Math.toRadians(angle);
        float g = Constants.g;

        force.Set(mass * g * (float)Math.sin(angleInRadian),
                  mass * g * (float)Math.cos(angleInRadian));

        HandleSlope();

//        System.out.println("notMoving: " + is + " velocity: " + velocity + " velocityMagnitude: " + velocity.Magnitude());
        UpdateAcceleration();
        UpdateObjectPosition();
        UpdateVelocity();


        collidedEdgeAngle = 0.0f;
        hasCollided = false;
    }

    private void HandleSlope() {
        if (gameObject.collider == null || !hasCollided) return;

        float g = Constants.g;
        notMoving = IsNotMoving();
        System.out.println("notMoving: " + notMoving + " velocity: " + velocity + " velocityMagnitude: " + velocity.Magnitude());

        if (notMoving) {
            isOnSlope = true;
        }


        if (isOnSlope) {
//            float thetaInRadian   = (float)Math.toRadians(collidedEdgeAngle);
//            float fN              = mass * (float)Math.cos(thetaInRadian) * g;
//            float fStaticFriction = fN * Constants.staticFrictionCoefficient;
//
//            System.out.println("collidedAngle " + collidedEdgeAngle + " staticF: " + fStaticFriction + " force: " + force + " forcemag: " + force.Magnitude());
//            if (CanSlideOnSlope(fStaticFriction, force)) {
//                ApplyDynamicFriction(fN);
//            }
//            else {
//                force.Set(0.0f, 0.0f);
//            }
        }




    }

    private void ApplyDynamicFriction(float fN) {
        float fDynamicFriction = fN * Constants.kineticFrictionCoefficient;
        Vector2 moveDirection = velocity.Normalize();
        Vector2 friction = moveDirection.ScalarProduct(fDynamicFriction);
        System.out.print("FN : " + fN + " Dynamic Friction: " + friction);
        force.Set(
                force.x - friction.x,
                force.y - friction.y
        );
        System.out.println(" After Dynamic friction: " + force);
    }


    private boolean CanSlideOnSlope(float fStaticFriction, Vector2 force) {
        return force.Magnitude() > fStaticFriction;
    }


    private boolean IsNotMoving()  { // checks if velocity is near zero
        return velocity.Magnitude() < Constants.velocityThreshold;
    }

    private void UpdateAcceleration() {
        System.out.print("Acceleration before: " + acceleration);
        acceleration.Set(
                (force.x / mass) * Constants.accelerationMultiplier,
                (force.y / mass) * Constants.accelerationMultiplier
        );
        System.out.println(" Acceleration after: " + acceleration);
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

        System.out.println("collidedAngle " + collidedEdgeAngle);


            UpdatePositionAfterCollision(other);

            Bounce(other);

//        System.out.println(
//                "ball pos: " + gameObject.transform.position + " ball center:" + gameObject.collider.bounds.center + "\n" +
//                        "ball min: " + gameObject.collider.bounds.GetMin() + " ball max: " + gameObject.collider.bounds.GetMax() + "\n" +
//                        "box pos: " + other.gameObject.transform.position + " box center:" + other.bounds.center + "\n" +
//                        "box min: " + other.bounds.GetMin() + " box max: " + other.bounds.GetMax() + "\n"
//        );
    }

    private void PullbackVelocity(float h) {
        float angle = collidedEdgeAngle;
        float angleInRadian = (float)Math.toRadians(angle);
        float hX = (float)Math.sin(angleInRadian) * h;
        float hY = (float)Math.cos(angleInRadian) * h;

        velocity.y = Math.signum(lastVelocity.y) * (float) Math.sqrt(Math.pow(lastVelocity.y, 2) + 2 * acceleration.y * hY);
        velocity.x = Math.signum(lastVelocity.x) * (float) Math.sqrt(Math.pow(lastVelocity.x, 2) + 2 * acceleration.x * hX);
    }

    private void UpdatePositionAfterCollision(Collider other) {
        Vector2 min = gameObject.collider.bounds.GetMin();
        Vector2 max = gameObject.collider.bounds.GetMax();
        Vector2 otherMin = other.bounds.GetMin();
        Vector2 otherMax = other.bounds.GetMax();
        float h = 0;
        if (max.y > otherMax.y) { // bottom v.y
            h = Math.abs(lastMax.y - otherMax.y);
            gameObject.transform.position.Set(gameObject.transform.position.x, otherMax.y - gameObject.collider.bounds.size.y / 2);
        }
        if (min.y < otherMin.y) { // top v.y
            h = Math.abs(lastMin.y - otherMin.y);
            gameObject.transform.position.Set(gameObject.transform.position.x, otherMin.y + gameObject.collider.bounds.size.y / 2);
        }
        if (max.x > otherMax.x) { // right v.x
            h = Math.abs(lastMax.x - otherMax.x);
            gameObject.transform.position.Set(otherMax.x - gameObject.collider.bounds.size.x / 2, gameObject.transform.position.y);
        }
        if (min.x < otherMin.x) { // left v.x
            h = Math.abs(lastMin.x - otherMin.x);
            gameObject.transform.position.Set(otherMin.x + gameObject.collider.bounds.size.x / 2, gameObject.transform.position.y);
        }
        gameObject.collider.bounds.Update(gameObject.transform.position, gameObject.collider.bounds.size);
        PullbackVelocity(h);
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

    public boolean CanShoot() {
        if(isOnSlope && velocity.Magnitude() <= Constants.velocityThreshold)
            return true;

        return false;
    }

    public void Shoot() {
        this.acceleration.Set(Constants.g * (float)Math.sin(Math.toRadians(0)), Constants.g * (float)Math.cos(Math.toRadians(0)));
        velocity.Set(acceleration.x * 25, -1 * acceleration.y * 25);
        isOnSlope = false;
    }
}
