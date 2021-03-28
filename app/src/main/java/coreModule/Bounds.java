package coreModule;

public class Bounds {
    public Vector2 center;
    public Vector2 min;
    public Vector2 max;
    public Vector2 size;

    public Bounds(Vector2 center, Vector2 size) {
        this.center = center;
        this.max = new Vector2(center.x + size.x / 2, center.y + size.y / 2);
        this.min = new Vector2(center.x - size.x / 2, center.y - size.y / 2);
        this.size   = size;
    }



    public boolean Intersects(Bounds other) {
        return
            min.x       < other.min.x + other.size.x &&
            other.min.x < min.x + size.x &&
            min.y       < other.min.y + other.size.y &&
            other.min.y < min.y + size.y;
    }
}
