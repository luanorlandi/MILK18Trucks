package database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class DatabaseRoot {

    private Map<String, Farm> farmMap;
    private Map<String, Industry> industryMap;
    private Map<String, User> userMap;

    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference farmRef = rootRef.child("Farm");
    private final DatabaseReference industryRef = rootRef.child("Industry");
    private final DatabaseReference userRef = rootRef.child("User");

    private ChildEventListener farmListener;
    private ChildEventListener industryListener;
    private ChildEventListener userListener;

    public DatabaseRoot() {
        farmMap = new HashMap<>();
        industryMap = new HashMap<>();
        userMap = new HashMap<>();

        startFarmListener();
        startIndustryListener();
        startUserListener();
    }

    public void startFarmListener() {
        farmListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Farm farm = dataSnapshot.getValue(Farm.class);

                if(farm != null) {
                    farmMap.put(dataSnapshot.getKey(), farm);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Farm farm = dataSnapshot.getValue(Farm.class);

                if(farm != null) {
                    Farm oldFarm = farmMap.get(dataSnapshot.getKey());
                    oldFarm.update(farm);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                farmMap.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        farmRef.addChildEventListener(farmListener);
    }

    public void startIndustryListener() {
        industryListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Industry industry = dataSnapshot.getValue(Industry.class);

                if(industry != null) {
                    industryMap.put(dataSnapshot.getKey(), industry);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Industry industry = dataSnapshot.getValue(Industry.class);

                if(industry != null) {
                    Industry oldIndustry = industryMap.get(dataSnapshot.getKey());
                    oldIndustry.update(industry);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                industryMap.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        industryRef.addChildEventListener(industryListener);
    }

    public void startUserListener() {
        userListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);

                if(user != null) {
                    userMap.put(dataSnapshot.getKey(), user);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);

                if(user != null) {
                    User oldUser = userMap.get(dataSnapshot.getKey());
                    oldUser.update(user);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                userMap.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        userRef.addChildEventListener(userListener);
    }

    public void stopFarmListener() {
        farmRef.removeEventListener(farmListener);
    }

    public void stopIndustryListener() {
        industryRef.removeEventListener(industryListener);
    }

    public void stopUserListener() {
        userRef.removeEventListener(userListener);
    }

    public Map<String, Farm> getFarmMap() {
        return farmMap;
    }

    public Map<String, Industry> getIndustryMap() {
        return industryMap;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void showMap() {
        System.err.println("MAP LIST:");
        System.err.println("FARM LIST");
        for(Map.Entry<String, Farm> entry : farmMap.entrySet()) {
            Farm u = entry.getValue();
            System.err.println("FARM " + entry.getKey());
            System.err.println("FARM " + u.getCoordinate().getLatitude());
            System.err.println("FARM " + u.getCoordinate().getLongitude() + "\n");
        }
        System.err.println(" ");

        System.err.println("INDUSTRY LIST");
        for(Map.Entry<String, Industry> entry : industryMap.entrySet()) {
            Industry u = entry.getValue();
            System.err.println("INDUSTRY " + entry.getKey());
            System.err.println("INDUSTRY " + u.getCoordinate().getLatitude());
            System.err.println("INDUSTRY " + u.getCoordinate().getLongitude() + "\n");
        }
        System.err.println(" ");

        System.err.println("USER LIST");
        for(Map.Entry<String, User> entry : userMap.entrySet()) {
            User u = entry.getValue();
            System.err.println("USER " + entry.getKey());
            System.err.println("USER " + u.getCoordinate().getLatitude());
            System.err.println("USER " + u.getCoordinate().getLongitude() + "\n");
        }
        System.err.println("- - - - - - -");
    }
}
