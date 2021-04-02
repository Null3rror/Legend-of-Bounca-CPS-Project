package components;

import components.collision.Collider;
import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Time;
import coreModule.Vector2;


public class RigidBody {
    private final float mass;
    public boolean addForce;

    public Vector2 GetVelocity() {
        return velocity;
    }

    private Vector2 velocity;
    private Vector2 acceleration;
    private GameObject gameObject;
    private Vector2 force;
    private boolean notMoving;
    private float collidedEdgeAngle;
    private boolean hasCollided;
    private boolean firstUpdate;
    private Vector2 additionalForce;


    private Vector2 lastVelocity;
    private Vector2 lastPosition;
    private Vector2 lastMax;
    private Vector2 lastMin;
    private boolean isOnSlope;
    private boolean isStatic;
    private Vector2 lastSlopeNormal;



    public RigidBody(float mass, GameObject gameObject) {
        this.mass = mass;
        this.velocity = Vector2.Zero();
//        this.velocity.Set(0, 0);
        this.acceleration = Vector2.Zero();
        this.gameObject = gameObject;
        this.force = Vector2.Zero();
        this.additionalForce = Vector2.Zero();
        this.notMoving = false;
        this.hasCollided = false;
        firstUpdate = true;
        isStatic = false;
        lastSlopeNormal = Vector2.Zero();
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

        if(addForce) {
            AddForce(20);
        }
        force = force.Sum(additionalForce);
        additionalForce.Set(0, 0);

        notMoving = IsNotMoving();
        if (notMoving && isOnSlope && hasCollided) {
            isStatic = true;
            Stop();
        }
        else {
            isStatic = true;
            HandleSlope();

            UpdateAcceleration();
            UpdateObjectPosition();
            UpdateVelocity();
        }
        if (addForce) {
            lastVelocity = new Vector2(velocity);
            addForce = false;
        }

//        if (velocity.x < Constants.zeroThreshold) {
//            velocity.x = 0;
//        }
//        if (velocity.y < Constants.zeroThreshold) {
//            velocity.y = 0;
//        }


//        collidedEdgeAngle = 0.0f;
        hasCollided = false;
        firstUpdate = false;
    }

    private void Stop() {
        velocity.Set(0,0);
        acceleration.Set(0,0);
        force.Set(0, 0);
    }

    private void HandleSlope() {
        if (firstUpdate || gameObject.collider == null) return;

        float g = Constants.g;

//        System.out.println("notMoving: " + notMoving + " velocity: " + velocity + " velocityMagnitude: " + velocity.Magnitude());

        float thetaInRadian2     = (float)Math.toRadians(collidedEdgeAngle);
        float thetaInRadian     = (float)Math.toRadians(gameObject.transform.rotation);
        Vector2 fN              = new Vector2((float)Math.sin(thetaInRadian), (float)Math.cos(thetaInRadian)).ScalarProduct(force.Magnitude());
        Vector2 normal          = new Vector2(-(float)Math.sin(thetaInRadian2), -(float)Math.cos(thetaInRadian2));
        fN = fN.ElementWiseProduct(normal);
        Vector2 fStaticFriction = new Vector2(fN).ScalarProduct(Constants.staticFrictionCoefficient);

        Vector2 fNNormalized = fN.Normalize();
        if (notMoving && !isOnSlope && hasCollided) {
            isOnSlope = true;
            velocity.Set(-velocity.x * fNNormalized.y, -velocity.y * fNNormalized.x);
            lastVelocity.Set(0.0f , 0.0f);
//            force.Set(force.x + -fN.x, force.y + -fN.y);
        }


        if (isOnSlope) {
            force.x += fN.x;
            force.y += fN.y;
            if (CanSlideOnSlope(fStaticFriction, force)) {

                if (velocity.Magnitude() != 0) {
                    ApplyDynamicFriction(fN);
                }
            }
            else {
                isStatic = true;
                force.Set(0.0f, 0.0f);
                velocity.Set(0.0f, 0.0f);
            }
        }

    }

    private void ApplyDynamicFriction(Vector2 fN) {
        Vector2 moveDirection = velocity.Normalize();
        Vector2 fDynamicFriction = moveDirection.ScalarProduct(Constants.kineticFrictionCoefficient * fN.Magnitude());
//        Vector2 friction = moveDirection.ElementWiseProduct(fDynamicFriction);
//        System.out.print("FN : " + fN + " Dynamic Friction: " + fDynamicFriction);
        force.Set(
                force.x - fDynamicFriction.x,
                force.y - fDynamicFriction.y
        );
//        System.out.println(" After Dynamic friction: " + force);
    }


    private boolean CanSlideOnSlope(Vector2 fStaticFriction, Vector2 force) {
        return force.Magnitude() > fStaticFriction.Magnitude();
    }


    private boolean IsNotMoving()  { // checks if velocity is near zero
        return velocity.Magnitude() < Constants.velocityThreshold;
    }

    private void UpdateAcceleration() {
//        System.out.print("Acceleration before: " + acceleration);
        acceleration.Set(
                (force.x / mass) * Constants.accelerationMultiplier,
                (force.y / mass) * Constants.accelerationMultiplier
        );
//        System.out.println(" Acceleration after: " + acceleration);
    }

    private void UpdateVelocity() {
        float deltaTime = Time.DeltaTime();
//        System.out.print("Update Velocity: before: " + velocity);
        velocity.Set(
                acceleration.x * deltaTime + velocity.x,
                acceleration.y * deltaTime + velocity.y
        );
//        System.out.println(" Update Velocity: after: " + velocity);
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
//        isOnSlope = false;
        collidedEdgeAngle = gameObject.collider.bounds.GetCollidingEdgeAngle(other.bounds) % 360;
        Vector2 normal = gameObject.collider.bounds.CalculateHitPointNormal(other.bounds);
        normal = normal.Normalize();
        isOnSlope = false;
//        if (!isOnSlope) {
            lastSlopeNormal.Set(normal.x, normal.y);
//        }
//        System.out.println("collidedAngle " + collidedEdgeAngle);


        UpdatePositionAfterCollision(other);
//        if (!isOnSlope)
//        boolean t = lastSlopeNormal.equals(normal);
//        if (!t || !isOnSlope) {
            Bounce(normal);
//        }

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

        if (velocity.x != velocity.x) velocity.x = 0;
        if (velocity.y != velocity.y) velocity.y = 0;
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



    public void Bounce(Vector2 normal) {
//        System.out.println("normal: " + normal);
        float dot = normal.DotProduct(velocity); // dot = -velocity.y
//        System.out.print("Bounce: before: " + velocity);
        velocity.Set(
                velocity.x - 2 * dot * normal.x,
                velocity.y - 2 * dot * normal.y
        );
//        System.out.println(" Bounce: after: " + velocity);

        ApplyEnergyLoss();

    }

    private void ApplyEnergyLoss() {
//        Vector2 oldVelocity = new Vector2(velocity);
        velocity.Set(
                velocity.x * Constants.wastedEnergyCoefficient,
                velocity.y * Constants.wastedEnergyCoefficient
        );
//        System.out.println("Applying energy loss: velocity before: " + oldVelocity + " after: " + velocity);

    }

    public boolean GetStaticStatus() {
        return isStatic;
    }


    public void AddForce(float multiplier) {

        float dirX = (float)(Math.random() * 2) - 1;
        float dirY = ((float) Math.random() * 2) - 1;
        additionalForce.Set(dirX * multiplier, dirY * multiplier);
        isOnSlope = false;
    }
}
