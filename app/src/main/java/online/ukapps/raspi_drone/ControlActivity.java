package online.ukapps.raspi_drone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.base.HTextView;

import java.util.Timer;
import java.util.TimerTask;

public class ControlActivity extends MainActivity implements SensorEventListener {

    HTextView header;
    CustomCameraView raspiCam;
    boolean defaultText = false;
    private TextView dynadata, dynadata2, dynadata3, dynadata4, dynadata5, dynadata6;
    boolean ready = true;

    //START @AUTHOR gem
    boolean mode = false;
    //STOP @AUTHOR gem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        header = findViewById(R.id.main_head);
        raspiCam = findViewById(R.id.camera);
        dynadata = findViewById(R.id.dynadata1);
        dynadata2 = findViewById(R.id.dynadata2);
        dynadata3 = findViewById(R.id.dynadata3);
        dynadata4 = findViewById(R.id.dynadata4);
        dynadata5 = findViewById(R.id.dynadata5);
        dynadata6 = findViewById(R.id.dynadata6);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, 5, new Handler());
            startUpdateText();
        }

        raspiCam.setOnTouchListener(new onSwipeTouchListener(this){
            public void onSwipeTop(){
                Toast.makeText(ControlActivity.this, "Swipe Up Detected", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight(){
                Toast.makeText(ControlActivity.this, "Swipe Right Detected", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft(){
                Toast.makeText(ControlActivity.this, "Swipe Left Detected", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom(){
                Toast.makeText(ControlActivity.this, "Swipe Bottom Detected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (ready) {
            ready = false;
            new Handler().postDelayed(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    dynadata.setText(String.valueOf((event.values[1]) * 42).substring(0, 4) + "m");
                    dynadata2.setText(String.valueOf(event.values[2]).substring(0, 4) + "m/s");
                    dynadata3.setText(String.valueOf(event.values[1] * event.values[2]).substring(0, 4) + "°");
                    dynadata4.setText(String.valueOf(event.values[1] * 2.214).substring(0, 4) + "°");
                    dynadata5.setText(String.valueOf(event.values[2] * 2.214).substring(0, 4) + "ms");
                    dynadata6.setText(String.valueOf(event.values[1] + event.values[2] * event.values[1]).substring(0, 4) + "m/s");
                    ready = true;
                }
            }, 1580);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing here.
    }

    void startUpdateText(){
        Timer setHeaderTimer = new Timer();
        TimerTask setHeaderTask = new TimerTask() {
            @Override
            public void run() {
                if (defaultText && mode) {
                    header.animateText(" RasPi");
                    defaultText = false;
                    mode = false;
                } else {
                    header.animateText(" Drone");
                    defaultText = true;
                    mode = true;
                }
            }
        };

        setHeaderTimer.schedule(setHeaderTask, 1500, 1500);
    }

    @Override
    protected void onResume(){
        super.onResume();
        raspiCam.start();
    }

    @Override
    protected void onPause(){
        raspiCam.stop();
        super.onPause();
    }
}
