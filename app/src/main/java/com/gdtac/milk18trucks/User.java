package com.gdtac.milk18trucks;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Orlandi on 13/11/2016.
 */

public class User implements Runnable {

    private DatabaseReference ref;
    private FirebaseUser user;
    private GPSTracker gpsTracker;

    private CoordenateInformation coord;

    public User(DatabaseReference ref, FirebaseUser user, GPSTracker gpsTracker) {
        this.ref = ref;
        this.user = user;
        this.gpsTracker = gpsTracker;

        coord = new CoordenateInformation(0, 0);
    }

    @Override
    public void run() {
        while(true) {
            Location location = gpsTracker.getLocation();

            if(location != null) {
                coord.setLongitude(location.getLongitude());
                coord.setLatitude(location.getLatitude());

                ref.child(user.getUid()).setValue(coord);
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
