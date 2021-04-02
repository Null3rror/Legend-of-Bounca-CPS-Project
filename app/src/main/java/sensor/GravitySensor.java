package sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import coreModule.Constants;
import coreModule.Vector3;

public class GravitySensor extends  SensorBase {


    public GravitySensor(SensorManager sensorManager) {
        super(sensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }



}
