package com.gdtac.milk18trucks;

import java.util.Map;

import database.DatabaseRoot;
import database.Farm;
import database.Industry;
import database.User;

/**
 * Created by Orlandi on 05/12/2016.
 */

public class MarkerHandler implements Runnable {

    private boolean enabled;

    private DatabaseRoot databaseRoot;
    private MapsFragment mapsFragment;

    private static final int MIN_TIME_UPDATE = 5000;


    public MarkerHandler(DatabaseRoot databaseRoot, MapsFragment mapsFragment, MainActivity mainActivity) {
        this.databaseRoot = databaseRoot;
        this.mapsFragment = mapsFragment;

        enabled = true;
    }

    @Override
    public void run() {
        while(enabled) {
            databaseRoot.showMap();

            for (Map.Entry<String, Farm> entry : databaseRoot.getFarmMap().entrySet()) {
                mapsFragment.updateFarmMarker(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<String, Industry> entry : databaseRoot.getIndustryMap().entrySet()) {
                mapsFragment.updateIndustryMarker(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<String, User> entry : databaseRoot.getUserMap().entrySet()) {
                mapsFragment.updateUserMarker(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<String, Farm> entry : databaseRoot.getGarbageFarmMap().entrySet()) {
                mapsFragment.removeFarmMarker(entry.getKey(), entry.getValue());
                databaseRoot.getGarbageFarmMap().remove(entry.getKey());
            }

            for (Map.Entry<String, Industry> entry : databaseRoot.getGarbageIndustryMap().entrySet()) {
                mapsFragment.removeIndustryMarker(entry.getKey(), entry.getValue());
                databaseRoot.getGarbageIndustryMap().remove(entry.getKey());
            }

            for (Map.Entry<String, User> entry : databaseRoot.getGarbageUserMap().entrySet()) {
                mapsFragment.removeUserMarker(entry.getKey(), entry.getValue());
                databaseRoot.getGarbageUserMap().remove(entry.getKey());
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
