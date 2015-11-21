package com.example.tinoba.liveball;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by antonio on 21/11/15.
 */
public class MapFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;
    private String provider;
    private Location location;
    private Context context;
    private List<Address> address;

    public MapFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        try {
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            } else {
                //textView.setText("fuck you, no location");
            }
        } catch (SecurityException e){
            Log.i("Security Permission", "not granted");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } catch (SecurityException e){
            Log.i("Security Permission", "not granted");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } catch (SecurityException e){
            Log.i("Security Permission", "not granted");
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e1){
            Log.e("Location", "getFromLocation");
        }
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        //textView.setText(String.valueOf(lat) + ", " + String.valueOf(lng) + " - " + address.get(0).getLocality());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getContext(), "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getContext(), "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}