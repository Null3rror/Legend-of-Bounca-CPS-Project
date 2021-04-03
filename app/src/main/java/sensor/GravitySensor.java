package sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import coreModule.Constants;
import coreModule.Vector3;
import coreModule.Vector4;

public class GravitySensor extends  SensorBase {


    public GravitySensor(SensorManager sensorManager) {
        super(sensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public Vector4 GetData() {
//        System.out.println("Gravity");
        float g = Constants.g;

        Vector4 angles = new Vector4(
                (float) Math.asin(data.x / g) % 360,
                (float) Math.asin(data.y / g) % 360,
                (float) Math.asin(data.z / g) % 360,
                0.0f
        );
        return angles;
    }
}
