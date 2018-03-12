package online.ukapps.raspi_drone;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;

public class MaterialSettings extends MainActivity {

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_settings);
        this.setTitle("Drone Settings");

        SettingsFragment preferenceFragment = new SettingsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.pref_container, preferenceFragment);
        ft.commit();
    }
}
