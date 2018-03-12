package online.ukapps.raspi_drone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //START @AUTHOR gem
        final Switch mode = findViewById(R.id.switchMode);
        Spinner spinner = findViewById(R.id.altitudeSpinner);

        String[] altitudes = {
                "50m",
                "100m",
                "150m",
                "200m",
                "250m",
                "300m",
                "350m",
                "400m",
                "450m",
                "500m"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                altitudes
        );

        spinner.setAdapter(adapter);
        spinner.setSelection(4, true);

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.getText() == getString(R.string.manual)){
                    mode.setText(getString(R.string.autopilot));
                } else {
                    mode.setText(getString(R.string.manual));
                }
            }
        });
        //END @AUTHOR gem
    }
}
