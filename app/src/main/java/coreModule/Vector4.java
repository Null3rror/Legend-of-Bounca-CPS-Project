package coreModule;

public class Vector4 {
    public float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        Set(x, y, z, w);
    }

    public void Set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + z + ", " + w;
    }
}
