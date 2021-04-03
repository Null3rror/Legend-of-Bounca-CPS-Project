package sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import coreModule.Vector4;

public class GyroscopeSensor extends SensorBase {

    private Vector4 angles;
    public GyroscopeSensor(SensorManager sensorManager) {
        super(sensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        angles = new Vector4(0, 0, 0, 0);
    }

    @Override
    public Vector4 GetData() {
//        System.out.println("Gyroscope");
        float deltaTime = data.w;

        angles.z = (angles.z + data.z * deltaTime) % 360;
        angles.y = angles.z;
        angles.x = 90 - angles.y;
        angles.w = data.w;
//        angles.Set(
//                90f - (angles.z + data.z * deltaTime) % 360 ,
//                (angles.z + data.z * deltaTime) % 360,
//                (angles.z + data.z * deltaTime) % 360,
//                data.w
//        );

        return angles;
    }
}
