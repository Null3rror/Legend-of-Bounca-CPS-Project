package coreModule;

public class Vector3 {
    public float x, y, z;

    public Vector3(float x, float y, float z) {
        Set(x, y, z);
    }

    public void Set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }
}
