package com.example.esamecomputazionepervasiva;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private boolean fine_location;
    public static final int POSITION_CODE_MAPS_ACTIVITY = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.i("developer", "Sono in Maps");


        Intent result= getIntent();
        fine_location=result.getExtras().getBoolean("fine_position");


        Log.i("developer: ", "fine_location: "+fine_location);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if((!MapsActivity.check_FINE_LOCATION(MapsActivity.this))){

            Utility.requestPermission(MapsActivity.this, POSITION_CODE_MAPS_ACTIVITY);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), SettingAccuracyActivity.class));
        finish();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case POSITION_CODE_MAPS_ACTIVITY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        startLocationUpdates();
                    }

                } else {
                    Toast.makeText(MapsActivity.this,"Permesso negato l'app non può funzionare", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }



    private void startLocationUpdates() {

            fusedLocationClient.requestLocationUpdates(this.createLocationRequest(),locationCallback, Looper.getMainLooper());
    }



    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        if(fine_location){
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(500);
        }else{
            locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
            locationRequest.setInterval(6000);
            locationRequest.setFastestInterval(500);
        }
        return locationRequest;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker and move the camera
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mMap.clear();

                    Log.i("developer: ", "Position: lat="+location.getLatitude() +" lon="+location.getLongitude() );

                    LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(pos).title("Current position: "+"lat="+location.getLongitude()+" lan="+location.getLatitude()));
                    float zoomLevel = 18.0f;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,zoomLevel));

                }
            }
        };


        startLocationUpdates();

    }
    public static boolean check_FINE_LOCATION(Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

}
