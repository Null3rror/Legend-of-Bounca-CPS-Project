package components;

import coreModule.Constants;
import coreModule.GameObject;
import coreModule.Vector4;
import gameObjects.SensorGameObject;

public class SensorReader {
    private SensorGameObject sensor;

    public SensorReader() {
        sensor = (SensorGameObject) GameObject.FindByTag(Constants.sensorTag);
    }

    public Vector4 GetAngles() {
        if (sensor == null)
            return Vector4.Zero();
        return sensor.GetAngles();
    }

    public Vector4 GetRawData() {
        if (sensor == null)
            return Vector4.Zero();
        return sensor.GetRawData();
    }
}
