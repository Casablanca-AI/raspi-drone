package online.ukapps.raspi_drone;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        EditText ip = findViewById(R.id.ipaddr);
        Button conn = findViewById(R.id.connect);
        TextView iphead = findViewById(R.id.iphead);
        RelativeLayout bg = findViewById(R.id.backgr);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ip.getLayoutParams().width = dm.widthPixels - 220;
        ip.requestLayout();

        new FlowingGradientClass()
                .setBackgroundResource(R.drawable.animation_list)
                .onRelativeLayout(bg)
                .setTransitionDuration(3250)
                .start();

        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ControlActivity.class));
                Toast.makeText(MainActivity.this, "connection successfully failed!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ip.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
        conn.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
        iphead.setTypeface(Typeface.createFromAsset(getAssets(), "psb.ttf"));
    }
}
