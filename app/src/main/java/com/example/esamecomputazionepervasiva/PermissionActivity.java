package com.example.esamecomputazionepervasiva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class PermissionActivity extends AppCompatActivity {

    public static final int POSITION_CODE_PERMISSION_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Button autorizza = findViewById(R.id.autorizza);
        Log.i("developer", "Sono in Permission");

            autorizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((!PermissionActivity.check_FINE_LOCATION(PermissionActivity.this))){
                        Utility.requestPermission(PermissionActivity.this,POSITION_CODE_PERMISSION_ACTIVITY);
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), SettingAccuracyActivity.class));
                        finish();
                    }
                }
            });
        }




    public static boolean check_FINE_LOCATION(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }







    //Receiver alert dialog permessi
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case POSITION_CODE_PERMISSION_ACTIVITY: {
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
                    Toast.makeText(PermissionActivity.this,R.string.permission_denied, Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


}
