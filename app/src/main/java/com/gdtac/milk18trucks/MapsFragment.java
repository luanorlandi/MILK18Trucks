package com.gdtac.milk18trucks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import database.DatabaseRoot;
import database.Farm;
import database.Industry;
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

    private DatabaseRoot databaseRoot;
    private boolean markerUpdate;

    private static final int MIN_TIME_UPDATE = 5000;

    private BitmapDescriptor farmMarkerIcon;
    private BitmapDescriptor industryMarkerIcon;
    private BitmapDescriptor userMarkerIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        farmMarker = new HashMap<>();
        industryMarker = new HashMap<>();
        userMarker = new HashMap<>();

        googleMap = null;

        /* must be set with function setDatabaseRoot() after create */
        databaseRoot = null;
        markerUpdate = true;

        /* set marker icons */
        farmMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_cow);
        industryMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_industry);
        userMarkerIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        markerUpdate = false;
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
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(), "Coord: " + latLng.toString(), Toast.LENGTH_SHORT).show();
    }

    public void updateFarmMarker(final String key, final Farm farm) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(googleMap == null) {
                    /* map not ready */
                    return;
                }

                if(farm == null) {
                    System.err.println("Maps fragment ignored a null farm");

                    return;
                }

                if(farmMarker.containsKey(key)) {
                    Marker marker = farmMarker.get(key);

                    marker.setPosition(new LatLng(
                            farm.getCoordinate().getLatitude(),
                            farm.getCoordinate().getLongitude()));
                    marker.setTitle(farm.getName());
                    marker.setSnippet(farm.getDescription());
                } else {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(
                            farm.getCoordinate().getLatitude(),
                            farm.getCoordinate().getLongitude()));
                    markerOptions.title(farm.getName());
                    markerOptions.snippet(farm.getDescription());
                    markerOptions.icon(farmMarkerIcon);

                    /* create the marker and add to map */
                    Marker marker = googleMap.addMarker(markerOptions);
                    farmMarker.put(key, marker);
                }
            }
        });
    }

    public void updateIndustryMarker(final String key, final Industry industry) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(googleMap == null) {
                    /* map not ready */
                    return;
                }

                if(industry == null) {
                    System.err.println("Maps fragment ignored a null industry");

                    return;
                }

                if(industryMarker.containsKey(key)) {
                    Marker marker = industryMarker.get(key);

                    marker.setPosition(new LatLng(
                            industry.getCoordinate().getLatitude(),
                            industry.getCoordinate().getLongitude()));
                    marker.setTitle(industry.getName());
                    marker.setSnippet(industry.getDescription());
                } else {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(
                            industry.getCoordinate().getLatitude(),
                            industry.getCoordinate().getLongitude()));
                    markerOptions.title(industry.getName());
                    markerOptions.snippet(industry.getDescription());
                    markerOptions.icon(industryMarkerIcon);

                    /* create the marker and add to map */
                    Marker marker = googleMap.addMarker(markerOptions);
                    industryMarker.put(key, marker);
                }
            }
        });
    }

    public void updateUserMarker(final String key, final User user) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                    marker.setTitle(user.getName());
                    marker.setSnippet(user.getDescription());
                } else {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(
                            user.getCoordinate().getLatitude(),
                            user.getCoordinate().getLongitude()));
                    markerOptions.title(user.getName());
                    markerOptions.snippet(user.getDescription());
                    markerOptions.icon(userMarkerIcon);

                    /* create the marker and add to map */
                    Marker marker = googleMap.addMarker(markerOptions);
                    userMarker.put(key, marker);
                }
            }
        });
    }

    public void removeFarmMarker(final String key, final Farm farm) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(googleMap == null) {
                    /* map not ready */
                    return;
                }

                if(farm == null) {
                    System.err.println("Maps fragment ignored a null farm");

                    return;
                }

                if(farmMarker.containsKey(key)) {
                    Marker marker = farmMarker.get(key);
                    marker.remove();
                } else {
                    System.err.println("Maps fragment receveid a farm that don't exist (" + key + ")");
                }
            }
        });
    }

    public void removeIndustryMarker(final String key, final Industry industry) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(googleMap == null) {
                    /* map not ready */
                    return;
                }

                if(industry == null) {
                    System.err.println("Maps fragment ignored a null user");

                    return;
                }

                if(industryMarker.containsKey(key)) {
                    Marker marker = industryMarker.get(key);
                    marker.remove();
                } else {
                    System.err.println("Maps fragment receveid a industry that don't exist (" + key + ")");
                }
            }
        });
    }

    public void removeUserMarker(final String key, final User user) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                    marker.remove();
                } else {
                    System.err.println("Maps fragment receveid a user that don't exist (" + key + ")");
                }
            }
        });
    }

    public DatabaseRoot getDatabaseRoot() {
        return databaseRoot;
    }

    public void setDatabaseRoot(DatabaseRoot databaseRoot) {
        this.databaseRoot = databaseRoot;
    }

    public boolean isMarkerUpdate() {
        return markerUpdate;
    }

    public void setMarkerUpdate(boolean markerUpdate) {
        this.markerUpdate = markerUpdate;
    }
}