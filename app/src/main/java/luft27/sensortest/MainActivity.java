package luft27.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gyro = new TextView[]{
				(TextView) findViewById(R.id.gyroX),
				(TextView) findViewById(R.id.gyroY),
				(TextView) findViewById(R.id.gyroZ)
		};

		accel = new TextView[]{
				(TextView) findViewById(R.id.accelX),
				(TextView) findViewById(R.id.accelY),
				(TextView) findViewById(R.id.accelZ),
				(TextView) findViewById(R.id.accelModule)
		};

		mag = new TextView[]{
				(TextView) findViewById(R.id.magX),
				(TextView) findViewById(R.id.magY),
				(TextView) findViewById(R.id.magZ),
				(TextView) findViewById(R.id.magModule)
		};

		angles = new TextView[]{
				(TextView) findViewById(R.id.roll),
				(TextView) findViewById(R.id.pitch),
				(TextView) findViewById(R.id.yaw)
		};

		((Button) findViewById(R.id.buttonReset)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// not implemented
			}
		});

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerSensorListener();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterSensorListener();
	}

	private void registerSensorListener() {
		Sensor sensorGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		Sensor sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor sensorMag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		if (sensorGyro != null) {
			sensorManager.registerListener(this, sensorGyro, 100 * 1000);
		} else {
			for (TextView t : gyro) {
				t.setText("N/A");
			}
		}

		if (sensorAccel != null) {
			sensorManager.registerListener(this, sensorAccel, 100 * 1000);
		} else {
			for (TextView t : accel) {
				t.setText("N/A");
			}
		}

		if (sensorMag != null) {
			sensorManager.registerListener(this, sensorMag, 100 * 1000);
		} else {
			for (TextView t : mag) {
				t.setText("N/A");
			}
		}
	}

	private void unregisterSensorListener() {
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		double total;

		switch (sensorEvent.sensor.getType()) {
			case Sensor.TYPE_GYROSCOPE:
				for (int i = 0; i < 3; ++i) {
					gyro[i].setText(String.format("%.2f", sensorEvent.values[i]));
				}
				break;

			case Sensor.TYPE_ACCELEROMETER:
				total = 0;
				for (int i = 0; i < 3; ++i) {
					accel[i].setText(String.format("%.2f", sensorEvent.values[i]));
					total += sensorEvent.values[i] * sensorEvent.values[i];
				}

				accel[3].setText(String.format("%.2f", Math.sqrt(total)));
				break;

			case Sensor.TYPE_MAGNETIC_FIELD:
				total = 0;
				for (int i = 0; i < 3; ++i) {
					mag[i].setText(String.format("%.2f", sensorEvent.values[i]));
					total += sensorEvent.values[i] * sensorEvent.values[i];
				}

				mag[3].setText(String.format("%.2f", Math.sqrt(total)));
				break;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {
	}

	private TextView[] gyro;
	private TextView[] accel;
	private TextView[] mag;
	private TextView[] angles;
	private SensorManager sensorManager;
}
