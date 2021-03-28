package components.collision;

public interface Collider {
    public boolean DetectCollision(Collider other);
    public void Update();
}
