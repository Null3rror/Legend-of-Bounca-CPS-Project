package components;

import coreModule.Vector2;
import coreModule.Vector3;

public class Transform {
    public Vector2 position, size;
    public Vector3 rotation;



    public Transform(float posX, float posY, float sizeX, float sizeY) {
        position = new Vector2(posX, posY);
        size     = new Vector2(sizeX, sizeY);
        this.rotation = new Vector3(0, 0, 0);
    }

    public Transform() {
        Reset();
    }

    public void Reset() {
        position = new Vector2(0.0f, 0.0f);
        size     = new Vector2(1.0f, 1.0f);
        this.rotation = new Vector3(0, 0, 0);
    }




}
