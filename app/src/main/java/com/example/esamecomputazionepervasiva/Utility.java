package com.example.esamecomputazionepervasiva;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class Utility {


    public static void requestPermission(final Activity activity, final int positionCode) {


        // Permission is not granted
        // Should we show an explanation? (Per vecchie API)

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(activity).setTitle(R.string.title_alert).setMessage(R.string.permission).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, positionCode);
                }

            })
                    .create()
                    .show();

        } else {
            //Permission Request at runtime
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, positionCode);
        }

    }

}
