package gameObjects;

import java.util.List;

import coreModule.GameObject;
import coreModule.Vector4;
import sensor.SensorBase;

public class SensorGameObject extends GameObject {
    private SensorBase sensor;
    public SensorGameObject(String tag, List<String> tagsToCheckCollisionWith, SensorBase sensor) {
        super(tag, tagsToCheckCollisionWith);
        this.sensor = sensor;
    }

    public Vector4 GetAngles() {
        return sensor.GetData();
    }

    public Vector4 GetRawData() {
        return sensor.GetRawData();
    }
}
