package components;

import coreModule.Vector2;

public class Transform {
    public Vector2 position, size;

    public void Reset() {
        position.Set(0.0f, 0.0f);
        size.Set(0.0f, 0.0f);
    }
}
