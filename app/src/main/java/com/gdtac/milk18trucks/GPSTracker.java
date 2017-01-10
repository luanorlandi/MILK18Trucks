package com.gdtac.milk18trucks;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by Orlandi on 13/11/2016.
 */

public class GPSTracker extends Service implements LocationListener {

    private final Context context;

    private boolean isGPSEnabled;
    private boolean isNerworkEnabled;
    private boolean canGetLocation;

    private static final long MIN_DIST_UPDATE = 0;
    private static final long MIN_TIME_UPDATE = 5000;

    protected LocationManager locationManager;

    /* help avoid 2 alert window */
    private boolean alert;

    private Location location;

    public GPSTracker(Context context) {
        this.context = context;

        isGPSEnabled = false;
        canGetLocation = false;

        alert = false;

        initGPS();
    }

    public void initGPS() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNerworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNerworkEnabled) {
                showSettingsAlert();
            } else {
                /* at least one provider is available */
                canGetLocation = true;

                if(isNerworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_UPDATE,
                            MIN_DIST_UPDATE,
                            this);
                }

                if(isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_UPDATE,
                            MIN_DIST_UPDATE,
                            this);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void stopGPS() {
        if(locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public void showSettingsAlert() {
        if(!alert) {
            alert = true;

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

            alertDialog.setTitle(getResources().getString(R.string.gps_disabled_title));
            alertDialog.setMessage(getResources().getString(R.string.gps_disabled_ask_settings));
            alertDialog.setPositiveButton(getResources().getString(R.string.gps_disabled_positive_response), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);

                    alert = false;
                }
            });

            alertDialog.setNegativeButton(getResources().getString(R.string.gps_disabled_negative_response), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                    alert = false;
                }
            });

            alertDialog.show();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        initGPS();
    }

    @Override
    public void onProviderDisabled(String provider) {
        stopGPS();
        showSettingsAlert();
    }

    public Location getLocation() {
        return location;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }
}
