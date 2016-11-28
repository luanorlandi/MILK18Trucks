package com.gdtac.milk18trucks;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Orlandi on 13/11/2016.
 */

public class User implements Runnable {

    private boolean enabled;

    private DatabaseReference ref;
    private FirebaseUser user;
    private GPSTracker gpsTracker;

    private CoordenateInformation coord;

    private static final int MIN_TIME_UPDATE = 5000;

    public User(DatabaseReference ref, FirebaseUser user, GPSTracker gpsTracker) {
        this.ref = ref;
        this.user = user;
        this.gpsTracker = gpsTracker;

        coord = new CoordenateInformation(0, 0);

        enabled = true;
    }

    @Override
    public void run() {
        while(enabled) {
            Location location = gpsTracker.getLocation();

            if (location != null) {
                coord.setLongitude(location.getLongitude());
                coord.setLatitude(location.getLatitude());

                ref.child(user.getUid()).setValue(coord);
            }

            try {
                Thread.sleep(MIN_TIME_UPDATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
