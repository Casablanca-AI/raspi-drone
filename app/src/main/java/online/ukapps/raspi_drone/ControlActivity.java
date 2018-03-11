package online.ukapps.raspi_drone;

import android.os.Bundle;
import com.google.android.cameraview.CameraView;
import com.hanks.htextview.base.HTextView;
import java.util.Timer;
import java.util.TimerTask;

public class ControlActivity extends MainActivity {

    HTextView header;
    CameraView raspiCam;
    boolean defaultText = false;

    //START @AUTHOR gem
    boolean mode = false;
    //STOP @AUTHOR gem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        header = findViewById(R.id.main_head);
        raspiCam = findViewById(R.id.camera);

        startUpdateText();
    }

    void startUpdateText(){
        Timer setHeaderTimer = new Timer();
        TimerTask setHeaderTask = new TimerTask() {
            @Override
            public void run() {
                if (defaultText) {
                    header.animateText(" RasPi");
                    defaultText = false;
                } else {
                    header.animateText(" Drone");
                    defaultText = true;
                }
            }
        };

        setHeaderTimer.schedule(setHeaderTask, 1500, 1550);
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
