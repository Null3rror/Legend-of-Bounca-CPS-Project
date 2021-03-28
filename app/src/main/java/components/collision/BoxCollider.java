package components.collision;

import coreModule.GameObject;
import coreModule.Vector2;

public class BoxCollider implements Collider {

    private GameObject gameObject;
    private Vector2 center;
    private Vector2 size;

    public BoxCollider(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void Update() {
        center = gameObject.transform.position;
        size   = gameObject.transform.size;
    }

    @Override
    public boolean DetectCollision(Collider other) {
        Update();
        other.Update();
        return false;
    }
}
