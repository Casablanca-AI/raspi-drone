package online.ukapps.raspi_drone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.andretietz.android.controller.DirectionView;
import com.andretietz.android.controller.InputView;
import com.hanks.htextview.base.HTextView;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class ControlActivity extends MainActivity implements SensorEventListener {

    HTextView header;
    CustomCameraView raspiCam;
    boolean defaultText = false;
    private TextView dynadata, dynadata2, dynadata3, dynadata4, dynadata5, dynadata6;
    boolean ready = true;
    String ipAddress;
    TextView dPadOutput, dPadOutputRight;
    DirectionView leftDirectionView;
    DirectionView rightDirectionView;

    //START @AUTHOR gem
    String damn = "up";
    boolean mode = false;
    //STOP @AUTHOR gem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ipAddress = String.valueOf(getIntent().getStringExtra("IPAddress"));

        leftDirectionView = findViewById(R.id.viewDirectionLeft);
        rightDirectionView = findViewById(R.id.viewDirectionRight);
        dPadOutput = findViewById(R.id.dPadOutput);
        dPadOutputRight = findViewById(R.id.dPadOutputRight);
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
                damn="up";
                new sendRequest().execute();
                Toasty.success(ControlActivity.this, "Up").show();
            }
            public void onSwipeRight(){
                damn="right";
                new sendRequest().execute();
                Toasty.success(ControlActivity.this, "Right").show();
            }
            public void onSwipeLeft(){
                damn="left";
                new sendRequest().execute();
                Toasty.success(ControlActivity.this, "Left").show();
            }
            public void onSwipeBottom(){
                damn="down";
                new sendRequest().execute();
                Toasty.success(ControlActivity.this, "Down").show();
            }
        });

        leftDirectionView.setOnButtonListener(new InputView.InputEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onInputEvent(View view, int buttons) {
                switch (buttons&0xff){
                    case DirectionView.DIRECTION_DOWN:
                        dPadOutput.setText("LEFT -> Down");
                        break;
                    case DirectionView.DIRECTION_DOWN_LEFT:
                        dPadOutput.setText("LEFT -> Down_Left");
                        break;
                    case DirectionView.DIRECTION_DOWN_RIGHT:
                        dPadOutput.setText("LEFT -> Down_Right");
                        break;
                    case DirectionView.DIRECTION_UP:
                        dPadOutput.setText("LEFT -> Up");
                        break;
                    case DirectionView.DIRECTION_UP_LEFT:
                        dPadOutput.setText("LEFT -> Up_Left");
                        break;
                    case DirectionView.DIRECTION_UP_RIGHT:
                        dPadOutput.setText("LEFT -> Up_Right");
                        break;
                    case DirectionView.DIRECTION_LEFT:
                        dPadOutput.setText("LEFT -> Left");
                        break;
                    case DirectionView.DIRECTION_RIGHT:
                        dPadOutput.setText("LEFT -> Right");
                        break;
                    default:
                        dPadOutput.setText("");
                }
            }
        });

        rightDirectionView.setOnButtonListener(new InputView.InputEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onInputEvent(View view, int buttons) {
                switch (buttons&0xff){
                    case DirectionView.DIRECTION_DOWN:
                        dPadOutputRight.setText("RIGHT -> Down");
                        break;
                    case DirectionView.DIRECTION_DOWN_LEFT:
                        dPadOutputRight.setText("RIGHT -> Down_Left");
                        break;
                    case DirectionView.DIRECTION_DOWN_RIGHT:
                        dPadOutputRight.setText("RIGHT -> Down_Right");
                        break;
                    case DirectionView.DIRECTION_UP:
                        dPadOutputRight.setText("RIGHT -> Up");
                        break;
                    case DirectionView.DIRECTION_UP_LEFT:
                        dPadOutputRight.setText("RIGHT -> Up_Left");
                        break;
                    case DirectionView.DIRECTION_UP_RIGHT:
                        dPadOutputRight.setText("RIGHT -> Up_Right");
                        break;
                    case DirectionView.DIRECTION_LEFT:
                        dPadOutputRight.setText("RIGHT -> Left");
                        break;
                    case DirectionView.DIRECTION_RIGHT:
                        dPadOutputRight.setText("RIGHT -> Right");
                        break;
                    default:
                        dPadOutputRight.setText("");
                }
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
                    dynadata.setText(String.valueOf(Math.abs((event.values[1]) * 42)).substring(0, 4) + "m");
                    dynadata2.setText(String.valueOf(Math.abs(event.values[2])).substring(0, 4) + "m/s");
                    dynadata3.setText(String.valueOf(event.values[1] * event.values[2]).substring(0, 4) + "°");
                    dynadata4.setText(String.valueOf(event.values[1] * 2.214).substring(0, 4) + "°");
                    dynadata5.setText(String.valueOf(Math.abs(event.values[2] * 2.214)).substring(0, 4) + "ms");
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

    @SuppressLint("StaticFieldLeak")
    private class sendRequest extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Bridge.get("http://" + ipAddress + "/set-direction.php?dir=" + damn).request().response().asString();
            } catch (BridgeException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
