package sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.SensorType;

import coreModule.Constants;
import coreModule.Vector3;

import static android.content.Context.SENSOR_SERVICE;
import android.content.Context;
import static androidx.core.content.ContextCompat.getSystemService;

public class GyroscopeSensor extends Activity implements SensorEventListener {

    private float deltaTime;
    private float timestamp;
    private Sensor sensor;
    private SensorManager sensorManager;
//    private float xAxisRotationVelocity, yAxisRotationVelocity, zAxisRotationVelocity;
    private Vector3 rotationVelocity;

    public GyroscopeSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        rotationVelocity = new Vector3(0, 0, 0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        deltaTime = (event.timestamp - timestamp) * Constants.ns2s;
        if (timestamp != 0) {
            rotationVelocity.Set(
                    event.values[0],
                    event.values[1],
                    event.values[2]
            );
        }
        timestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
