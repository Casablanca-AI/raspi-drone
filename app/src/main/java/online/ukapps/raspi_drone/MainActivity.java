package online.ukapps.raspi_drone;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button connect = findViewById(R.id.connect);
        final EditText ip = findViewById(R.id.ipAddress);
        TextView ipHead = findViewById(R.id.ipHead);
        RelativeLayout bg = findViewById(R.id.backgroundIP);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ip.getLayoutParams().width = dm.widthPixels - 220;
        ip.requestLayout();

        //START @Author Gem
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter(
        ) {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dStart, int dEnd) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dStart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dEnd);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (String split : splits) {
                            if (Integer.valueOf(split) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        ip.setFilters(filters);
        //END @Author Gem

        new FlowingGradientClass()
                .setBackgroundResource(R.drawable.animation_list)
                .onRelativeLayout(bg)
                .setTransitionDuration(3250)
                .start();

        connect.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, MaterialSettings.class));
                return false;
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog goControls = new MaterialDialog.Builder(MainActivity.this)
                        .title("Please Wait")
                        .content("Connecting to your RasPi Drone")
                        .progress(true, 0)
                        .show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Connection successful!", Toast.LENGTH_SHORT).show();
                                goControls.dismiss();
                                startActivity(new Intent(MainActivity.this, ControlActivity.class)
                                .putExtra("IPAddress", ip.getText().toString()));
                                finish();
                            }
                        });
                    }
                }, 1000);
                //Temporary time gap to SIMULATE connecting delay.
            }
        });

        ip.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
        connect.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
        ipHead.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
    }
}
