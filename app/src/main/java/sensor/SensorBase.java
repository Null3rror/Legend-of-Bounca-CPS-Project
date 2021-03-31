package sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import coreModule.Constants;
import coreModule.Vector4;

public class SensorBase implements SensorEventListener {

    protected float deltaTime;
    protected float timestamp;
    protected Sensor sensor;
    protected SensorManager sensorManager;
    protected Vector4 data;

    public Vector4 GetData() {
        return data;
    }

    public SensorBase(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        data = new Vector4(0, 0, 0, 0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        deltaTime = (event.timestamp - timestamp) * Constants.ns2s;
        if (timestamp != 0) {
            data.Set(
                    event.values[0],
                    event.values[1],
                    event.values[2],
                    deltaTime
            );
        }
        timestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
