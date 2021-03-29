package components;

import coreModule.Vector2;

public class Transform {
    public Vector2 position, size;

    public void Reset() {
        position = new Vector2(0.0f, 0.0f);
        size     = new Vector2(1.0f, 1.0f);
    }
}
