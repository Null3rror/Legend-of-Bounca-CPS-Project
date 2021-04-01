package coreModule;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        Set(x, y);
    }

    public Vector2(Vector2 other) {
        this(other.x, other.y);
    }

    public static Vector2 Zero(){ return new Vector2(0, 0); }
    public void Set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float SqrMagnitude() {
        return x * x + y * y;
    }

    public float Magnitude() {
        return (float)Math.sqrt(SqrMagnitude());
    }

    public void Normalize() {
        float magnitude = Magnitude();

        ScalarDivide(magnitude);
    }


    public void ScalarDivide(float scalar) {
        if (scalar == 0.0f)
            return;
        x /= scalar;
        y /= scalar;
    }

    public Vector2 ElementWiseProduct(Vector2 other){ return new Vector2(x * other.x, y * other.y); }
    public float DotProduct(Vector2 other) {
        return x * other.x + y * other.y;
    }
    public Vector2 Sum(Vector2 other){ return new Vector2(x + other.x, y + other.y); }
    public Vector2 ScalarProduct(float scalar){ return ElementWiseProduct(new Vector2(scalar, scalar)); }


    public Vector2 Rotate(float angle) {
        double angleInRadian = Math.toRadians(angle);
        double newX = x * Math.cos(angleInRadian) - y * Math.sin(angleInRadian);
        double newY = x * Math.sin(angleInRadian) + y * Math.cos(angleInRadian);
        return new Vector2((float) newX, (float) newY);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 &&
                Float.compare(vector2.y, y) == 0;
    }



    @Override
    public String toString() {
        return x + ", " + y;
    }
}
