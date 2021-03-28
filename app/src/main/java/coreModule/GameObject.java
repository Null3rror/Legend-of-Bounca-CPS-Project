package coreModule;

import components.Transform;

public abstract class GameObject {
    public Transform transform;

    public abstract void Update();

    public abstract void Draw();
}
