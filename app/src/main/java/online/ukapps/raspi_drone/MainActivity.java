package online.ukapps.raspi_drone;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dynamitechetan.flowinggradient.FlowingGradientClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button connect = findViewById(R.id.connect);
        //EditText ip = findViewById(R.id.ipaddr);
        //TextView ipHead = findViewById(R.id.iphead);
        //RelativeLayout bg = findViewById(R.id.backgr);
        EditText ip = findViewById(R.id.ipAddress);
        TextView ipHead = findViewById(R.id.ipHead);
        RelativeLayout bg = findViewById(R.id.backgroundIP);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ip.getLayoutParams().width = dm.widthPixels - 220;
        ip.requestLayout();

        //START @Author Gempario
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
        //END @Author Gempario

        new FlowingGradientClass()
                .setBackgroundResource(R.drawable.animation_list)
                .onRelativeLayout(bg)
                .setTransitionDuration(3250)
                .start();

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ControlActivity.class));
                Toast.makeText(MainActivity.this, "connection successfully failed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ip.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
        connect.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
        ipHead.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
    }
}
