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

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gyro = new TextView[]{
				(TextView) findViewById(R.id.gyroX),
				(TextView) findViewById(R.id.gyroY),
				(TextView) findViewById(R.id.gyroZ)
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
		Sensor sensorGyro = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		if (sensorGyro != null) {
			sensorManager.registerListener(sensorListener, sensorGyro, 100 * 1000);
		} else {
			for (TextView t : gyro) {
				t.setText("N/A");
			}
		}
	}

	private void unregisterSensorListener() {
		sensorManager.unregisterListener(sensorListener);
	}

	private TextView[] gyro;
	private TextView[] angles;
	private SensorManager sensorManager;

	private SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {
			for (int i = 0; i < gyro.length; ++i) {
				gyro[i].setText(String.format("%.2f", sensorEvent.values[i]));
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int i) {
		}
	};
}
