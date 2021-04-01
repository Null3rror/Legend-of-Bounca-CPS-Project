package components;

import coreModule.Vector2;

public class Transform {
    public Vector2 position, size;
    public float rotation;



    public Transform(float posX, float posY, float sizeX, float sizeY) {
        position = new Vector2(posX, posY);
        size     = new Vector2(sizeX, sizeY);
        this.rotation = 0f;
    }

    public Transform() {
        Reset();
    }

    public void Reset() {
        position = new Vector2(0.0f, 0.0f);
        size     = new Vector2(1.0f, 1.0f);
        rotation = 0;
    }




}
