package com.gdtac.milk18trucks;

import android.location.Location;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import database.Coordinate;
import database.User;

/**
 * Created by Orlandi on 13/11/2016.
 */

public class MyUser implements Runnable {

    private boolean enabled;

    private DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private GPSTracker gpsTracker;

    private User user;

    private static final int MIN_TIME_UPDATE = 5000;

    public MyUser(DatabaseReference ref, FirebaseUser firebaseUser, GPSTracker gpsTracker) {
        this.ref = ref;
        this.firebaseUser = firebaseUser;
        this.gpsTracker = gpsTracker;

        user = new User();

        enabled = true;
    }

    @Override
    public void run() {
        while(enabled) {
            Location location = null;

            System.err.println("STEP 1 LOCATION");

            if (gpsTracker != null) {
                location = gpsTracker.getLocation();
            }

            System.err.println("STEP 2 LOCATION");

            if (location != null) {
                System.err.println("STEP 3 LOCATION");
                System.err.println("GOT COORD = " + user.getCoordinate());
                user.getCoordinate().setLongitude(location.getLongitude());
                user.getCoordinate().setLatitude(location.getLatitude());

                ref.child(firebaseUser.getUid()).setValue(user);
            } else
                System.err.println("LOCATION IS NULL");


            System.err.println("STEP 4 LOCATION");

            try {
                System.err.println("STEP 5 LOCATION");
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
