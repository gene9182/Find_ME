package com.example.esamecomputazionepervasiva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class SettingAccuracyActivity extends AppCompatActivity {

    public static final int POSITION_CODE_SETTING_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_accuracy);
        Button high = findViewById(R.id.High);
        Button low = findViewById(R.id.Low);

        Toast.makeText(getApplicationContext(), R.string.gps, Toast.LENGTH_LONG).show();
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if(!gpsEnabled) {
                    enablePositionSettings();
                }else{
                    Intent high = new Intent(getApplicationContext(), MapsActivity.class);
                    high.putExtra("fine_position", true);
                    SettingAccuracyActivity.this.startActivity(high);
                    finish();
                }
            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent low = new Intent(getApplicationContext(), MapsActivity.class);
                low.putExtra("fine_location", false);
                SettingAccuracyActivity.this.startActivity(low);
                finish();

            }
        });

        Log.i("developer", "Sono in setting");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(( !SettingAccuracyActivity.check_FINE_LOCATION(SettingAccuracyActivity.this))){
            Utility.requestPermission(SettingAccuracyActivity.this,POSITION_CODE_SETTING_ACTIVITY);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case POSITION_CODE_SETTING_ACTIVITY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        startActivity(new Intent(getApplicationContext(), SettingAccuracyActivity.class));
                        finish();
                    }

                } else {
                    Toast.makeText(SettingAccuracyActivity.this,R.string.permission_denied, Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }
    public static boolean check_FINE_LOCATION(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void enablePositionSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    //MenuInflater istanzia un menu definito in XML in un Oggetto Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maps, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.credits:
                startActivity(new Intent(getApplicationContext(), CreditsActivity.class));


            case R.id.exit:
                finish();
            default:
                break;
        }

        return false;
    }




}
