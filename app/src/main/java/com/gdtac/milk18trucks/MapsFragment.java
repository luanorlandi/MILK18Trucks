package com.gdtac.milk18trucks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import database.Farm;
import database.User;

import static java.security.AccessController.getContext;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private static final String TAG = "MapsFragment";

    private GoogleMap googleMap;

    private Map<String, Marker> farmMarker;
    private Map<String, Marker> industryMarker;
    private Map<String, Marker> userMarker;

    private final static long locationUpdateTime = 1500;
    private final static float minDistUpdateTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        farmMarker = new HashMap<>();
        industryMarker = new HashMap<>();
        userMarker = new HashMap<>();

        googleMap = null;
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
    public void onMapReady(final GoogleMap googleMap) {

        try {
            this.googleMap = googleMap;
            this.googleMap.setOnMapClickListener(this);
            this.googleMap.getUiSettings().setZoomControlsEnabled(true);
            this.googleMap.setMyLocationEnabled(true);
        } catch(SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }

        new Runnable() {
            @Override
            public void run() {
                LatLng school = new LatLng(-22.004097, -47.855757);
                MarkerOptions marker = new MarkerOptions();
                marker.position(school);
                marker.title("School");
                marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Marker m = googleMap.addMarker(marker);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(school));
                    }
                }
            }
        };
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(), "Coord: " + latLng.toString(), Toast.LENGTH_SHORT).show();
    }

    public void updateUserMarker(String key, User user) {
        if(googleMap == null) {
            /* map not ready */
            return;
        }

        if(user == null) {
            System.err.println("Maps fragment ignored a null user");

            return;
        }

        if(userMarker.containsKey(key)) {
            Marker marker = userMarker.get(key);

            marker.setPosition(new LatLng(
                    user.getCoordinate().getLatitude(),
                    user.getCoordinate().getLongitude()));
        } else {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(
                    user.getCoordinate().getLatitude(),
                    user.getCoordinate().getLongitude()));
            markerOptions.title("User");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

            /* create the marker and add to map */
            //Marker marker = googleMap.addMarker(markerOptions);
            //userMarker.put(key, marker);
        }
    }
}