package coreModule;

import static coreModule.Constants.Rotation;
import static coreModule.Constants.offsetRotationBottom;
import static coreModule.Constants.offsetRotationLeft;
import static coreModule.Constants.offsetRotationRight;
import static coreModule.Constants.offsetRotationTop;

public class Bounds {
    public Vector2 center;

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

    public Bounds(Vector2 center, Vector2 size) {
        this.center = center;
        this.max = new Vector2(center.x + size.x / 2, center.y + size.y / 2);
        this.min = new Vector2(center.x - size.x / 2, center.y - size.y / 2);
        this.size   = size;
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

//    public Vector2 HasHitHorOrVer(Bounds other) {
////        Vector2 normal = Vector2.Zero();
////
////        if(other.min.x <= min.x || other.max.x >= max.x)  //left , right
////            normal = normal.Sum(Rotation(offsetRotationLeft));
////        if(other.min.y <= min.y || other.max.y >= max.y) //top , bottom
////            normal = normal.Sum(Rotation(offsetRotationTop));
//
//        return CalculateHitPointNormal(other);
//    }

    public boolean Intersects(Bounds other) {
        UpdateMinMax();
        other.UpdateMinMax();
        return !Contains(other) && !other.Contains(this) &&
            min.x       < other.max.x &&
            other.min.x < max.x       &&
            min.y       < other.max.y &&
            other.min.y < max.y;
    }

    public Vector2 CalculateHitPointNormal(Bounds other) {
        Vector2 normal = Vector2.Zero();

        if(other.min.x <= min.x)  //left
            normal = normal.Sum(Rotation(offsetRotationLeft));
        if(other.max.x >= max.x)  // right
            normal = normal.Sum(Rotation(offsetRotationRight));

        if(other.min.y <= min.y) //top
            normal = normal.Sum(Rotation(offsetRotationTop));
        if(other.max.y >= max.y) //bottom
            normal = normal.Sum(Rotation(offsetRotationBottom));

        return normal;
    }
}


    
