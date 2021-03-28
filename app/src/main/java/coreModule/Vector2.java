package coreModule;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        Set(x, y);
    }

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

        if (magnitude == 0.0f)
            return;

        x /= magnitude;
        y /= magnitude;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 &&
                Float.compare(vector2.y, y) == 0;
    }
}
