package ivanrudyk.com.open_weather_api.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Toast;

import ivanrudyk.com.open_weather_api.R;
import ivanrudyk.com.open_weather_api.helpers.Helper;

public class MySettingsActivity extends AppCompatActivity {

    SwitchCompat switchNetwork, switchGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Settings");
        setTitleColor(R.color.colorTitle);
        switchNetwork = (SwitchCompat) findViewById(R.id.switchNetwork);
        switchGPS = (SwitchCompat) findViewById(R.id.switchGPS);
        LocationManager locationManager = (LocationManager) getApplication()
                .getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            switchGPS.setChecked(true);
        }
        else
        {
            switchGPS.setChecked(false);
        }
        if (Helper.isNetworkAvailable(getApplicationContext()))
        {
            switchNetwork.setChecked(true);
        }
        else
        {
            switchNetwork.setChecked(false);
        }
        switchNetwork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!switchNetwork.isChecked())
                {
                    showSettingsAlertNetwork();
                }
                if (switchNetwork.isChecked())
                {
                    showSettingsAlertNetwork();
                }
            }
        });
        switchGPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LocationManager locationManager = (LocationManager) getApplication()
                        .getSystemService(LOCATION_SERVICE);
                if (!switchGPS.isChecked())
                {
                    showSettingsAlertGPS();
                }
                if (switchGPS.isChecked())
                {
                    showSettingsAlertGPS();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager locationManager = (LocationManager) getApplication()
                .getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            switchGPS.setChecked(true);
        }
        else
        {
            switchGPS.setChecked(false);
        }
        if (Helper.isNetworkAvailable(getApplicationContext()))
        {
            switchNetwork.setChecked(true);
        }
        else
        {
            switchNetwork.setChecked(false);
        }
    }

    public void showSettingsAlertGPS() {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
        // Setting Dialog Message
        alertDialog.setMessage("Your GPS menu settings.");

        // n pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                MySettingsActivity.this.startActivityForResult(intent, 2);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LocationManager locationManager = (LocationManager) getApplication()
                        .getSystemService(LOCATION_SERVICE);
                dialog.cancel();
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    switchGPS.setChecked(true);
                }
                else
                {
                    switchGPS.setChecked(false);
                    //
                }
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void showSettingsAlertNetwork() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Network is settings");
        // Setting Dialog Message
        alertDialog.setMessage("Your network menu settings.");

        // n pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                MySettingsActivity.this.startActivityForResult(intent , 1);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (Helper.isNetworkAvailable(getApplicationContext()))
                {
                    switchNetwork.setChecked(true);
                }
                else
                {
                    switchNetwork.setChecked(false);
                }
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1)
            {
                switchNetwork.setChecked(true);
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "Please, check your internet connection", Toast.LENGTH_LONG).show();
                switchNetwork.setChecked(false);
            }
            if (requestCode == 2)
            {
                switchGPS.setChecked(true);
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "Please, check your GPS settings", Toast.LENGTH_LONG).show();
                switchGPS.setChecked(false);
            }
        }
    }
}
