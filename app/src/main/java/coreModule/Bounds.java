package coreModule;

import components.collision.Collider;

public class Bounds {
    public Vector2 center;
    private Collider collider;

    public Vector2 GetMin() {
        UpdateMinMax();
        return min;
    }

    public Vector2 GetMax() {
        UpdateMinMax();
        return max;
    }

    public Vector2 min;
    public Vector2 max;
    public Vector2 size;

    public Bounds(Vector2 center, Vector2 size, Collider collider) {
        this.collider = collider;
        this.center = center;
        this.max = new Vector2(center.x + size.x / 2, center.y + size.y / 2);
        this.min = new Vector2(center.x - size.x / 2, center.y - size.y / 2);
        this.size   = size;
    }

    public void Update(Vector2 center, Vector2 size) {
        this.center = center;
        this.size   = size;
        UpdateMinMax();
    }

    public void UpdateMinMax() {
        this.max.Set(center.x + size.x / 2, center.y + size.y / 2);
        this.min.Set(center.x - size.x / 2, center.y - size.y / 2);
    }

    public boolean Contains(Bounds other) {
        UpdateMinMax();
        other.UpdateMinMax();
        return
                min.x       <= other.min.x &&
                other.max.x <= max.x       &&
                min.y       <= other.min.y &&
                other.max.y <= max.y;
    }



    public boolean Intersects(Bounds other) {
        UpdateMinMax();
        other.UpdateMinMax();
        return !Contains(other) && !other.Contains(this) &&
            min.x       < other.max.x &&
            other.min.x < max.x       &&
            min.y       < other.max.y &&
            other.min.y < max.y;
    }

    public Vector2 CalculateNormal(float angle) {
        float angleInRadian = (float)Math.toRadians(angle);
//        System.out.println("Angle in radian: " + angleInRadian);
        Vector2 normal = new Vector2(-(float)Math.sin(angleInRadian), -(float)Math.cos(angleInRadian));
        System.out.println("Normal in normal: " + normal + " mag: " + normal.Magnitude());
        return normal;
    }

    public float GetCollidingEdgeAngle(Bounds other) {
        UpdateMinMax();
        other.UpdateMinMax();
//        float angle = collider.gameObject.transform.rotation;
        float angle = 0;
        // collider.gameObject.transform.rotation;

        if(other.min.x >= min.x)  //left
        {
            System.out.println("Left Andf");
            angle += Constants.leftAngle;
        }
        if(other.max.x <= max.x)  // right
        {
                        System.out.println("right Andf");
            angle += Constants.rightAngle;
        }

        if(other.min.y >= min.y) //top
        {
                        System.out.println("top Andf");
            angle += Constants.ceilAngle;
        }
        if(other.max.y <= max.y) //bottom
        {
            angle += Constants.floorAngle;
                        System.out.println("bottom Andf " + angle);

        }
        return angle;
    }


    public Vector2 CalculateHitPointNormal(Bounds other) {
        UpdateMinMax();
        other.UpdateMinMax();
//        float angle = collider.gameObject.transform.rotation;;
        float angle = 0;
        Vector2 normal = Vector2.Zero();
//        System.out.println("Angle: " + angle);
        if(other.min.x > min.x) {  //left
//            System.out.println("Left");
            normal = normal.Sum(CalculateNormal(Constants.leftAngle + angle));
        }
        if(other.max.x < max.x) {  // right
//            System.out.println("Right");
            normal = normal.Sum(CalculateNormal(Constants.rightAngle + angle));
        }

        if(other.min.y > min.y) {//top
//            System.out.println("Top");
            normal = normal.Sum(CalculateNormal(Constants.ceilAngle + angle));
        }
        if(other.max.y < max.y) {//bottom
//            System.out.println("Bottom");
            normal = normal.Sum(CalculateNormal(Constants.floorAngle + angle));
        }


        return normal;
    }
}


    
