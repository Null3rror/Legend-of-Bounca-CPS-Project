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
}
