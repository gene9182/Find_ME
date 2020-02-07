package com.example.esamecomputazionepervasiva;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("developer", "Sono in Splash");

        setContentView(R.layout.activity_splash);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if (!SplashActivity.check_FINE_LOCATION(SplashActivity.this)) {
                    startActivity(new Intent(getApplicationContext(), PermissionActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), SettingAccuracyActivity.class));
                    finish();
                }
            }
        }, 3000);
    }


    public static boolean check_FINE_LOCATION(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }



}
