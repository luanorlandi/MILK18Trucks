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
import com.google.android.gms.maps.model.MarkerOptions;

import static java.security.AccessController.getContext;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private static final String TAG = "MapsFragment";

    private GoogleMap mMap;

    private final static long locationUpdateTime = 1500;
    private final static float minDistUpdateTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
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

        try {
            mMap = googleMap;
            mMap.setOnMapClickListener(this);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
        } catch(SecurityException ex) {
            Log.e(TAG, "Error", ex);
        }

        LatLng school = new LatLng(-22.004097, -47.855757);

        MarkerOptions marker = new MarkerOptions();
        marker.position(school);
        marker.title("School");
        marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(), "Coord: " + latLng.toString(), Toast.LENGTH_SHORT).show();
    }
}