package com.example.tinoba.liveball;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    List<Address> address;
    LocationManager locationManager;
    String provider;
    Location location;
    private ArrayList<Igrac> podaci;
    double longitude = 0;
    double latitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        final Location location;
        final GoogleMap mMap;
        mMap = googleMap;



        if (network_enabled) {
            if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {



            }

            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Thread thread = new Thread(){
                @Override
                public void run() {

                    while (true) {


                        if (location != null) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            Log.i("TAG", String.valueOf(latitude) + " " + String.valueOf(longitude));
                        }

                        LoginService loginService =
                                ServiceGenerator.createService(LoginService.class);

                        Call<ArrayList<Igrac>> call = loginService.pozicije(String.valueOf(latitude), String.valueOf(longitude), "6");
                        call.enqueue(new Callback<ArrayList<Igrac>>() {
                            @Override
                            public void onResponse(Response<ArrayList<Igrac>> response, Retrofit retrofit) {
                                podaci = new ArrayList<Igrac>(response.body());


                                Log.i("TAG", response.message());
                                if (response.body() != null) {
                                    for (Igrac s : podaci) {
                                        Log.i("TAG", s.getId() + s.getLat() + s.getLng());
                                        LatLng pozicija = new LatLng(Float.valueOf(s.getLat()), Float.valueOf(s.getLng()));
                                        //mMap.clear();
                                        mMap.addMarker(new MarkerOptions().position(pozicija).title("Trenutna pozicija"));

                                    }
                                    Log.i("TAG", response.body().toString());
                                } else Log.i("TAG", "no body");
                                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
                                CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
                                mMap.moveCamera(center);
                                mMap.animateCamera(zoom);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e("TAG", t.getMessage());
                            }
                        });
                     //   mMap.setMyLocationEnabled(true);

                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pozicija));
                        LocationManager locationManager = (LocationManager)
                                getSystemService(Context.LOCATION_SERVICE);


                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();









        }








    }




    public void onLocationChanged(Location location) {
        Log.i("TAG","bok");
        Geocoder geocoder = new Geocoder(Mapa.this, Locale.getDefault());
        try {
            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e1){
            Log.e("Location", "getFromLocation");
        }
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        Log.i("TAG", String.valueOf(lat) + " " + String.valueOf(lng));
        Log.i("TAG","BZVZ");

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        try {
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            } else {
                Log.i("TAG","nema gpsa");
            }
        } catch (SecurityException e){
            Log.i("Security Permission", "not granted");
        }




    }







}
