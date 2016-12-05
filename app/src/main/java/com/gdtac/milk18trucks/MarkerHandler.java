package com.gdtac.milk18trucks;

import java.util.Map;

import database.DatabaseRoot;
import database.User;

/**
 * Created by Orlandi on 05/12/2016.
 */

public class MarkerHandler implements Runnable {

    private boolean enabled;

    DatabaseRoot databaseRoot;
    MapsFragment mapsFragment;

    private static final int MIN_TIME_UPDATE = 5000;


    public MarkerHandler(DatabaseRoot databaseRoot, MapsFragment mapsFragment) {
        this.databaseRoot = databaseRoot;
        this.mapsFragment = mapsFragment;

        enabled = true;
    }

    @Override
    public void run() {
        while(enabled) {
            databaseRoot.showMap();

            for(Map.Entry<String, User> entry : databaseRoot.getUserMap().entrySet()) {
                mapsFragment.updateUserMarker(entry.getKey(), entry.getValue());
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
