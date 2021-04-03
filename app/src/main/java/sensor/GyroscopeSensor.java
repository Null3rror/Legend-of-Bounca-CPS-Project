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

        angles.z = (angles.z + data.z * deltaTime);
        angles.y = -angles.z;
        angles.x = (float) (Math.toRadians(90f) - angles.z);
        angles.w = data.w;
//        System.out.println("LALA: 27 ------> " + angles);
//        angles.Set(
//                ( - (angles.z + data.z * deltaTime)) % 360 ,
//                -((angles.z + data.z * deltaTime) % 360),
//                (angles.z + data.z * deltaTime) % 360,
//                data.w
//        );
//        angles.Set(
//                (angles.y + data.y * deltaTime) % 360,
//                (angles.x - data.x * deltaTime) % 360,
//                (angles.z + data.z * deltaTime) % 360,
//                data.w
//        );

        return angles;
    }
}
