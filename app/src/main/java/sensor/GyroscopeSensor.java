package sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class GyroscopeSensor extends SensorBase {

    public GyroscopeSensor(SensorManager sensorManager) {
        super(sensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }


}
