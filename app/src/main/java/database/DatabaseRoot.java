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

    Map<String, Farm> farmMap;
    Map<String, Industry> industryMap;
    Map<String, User> userMap;

    private DatabaseReference rootRef;

    public DatabaseRoot() {
        userMap = new HashMap<>();

        /*System.err.println("MAP LIST:");
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            User u = entry.getValue();
            System.err.println("MAP USER LIST " + entry.getKey());
            System.err.println("MAP USER LIST " + u.getCoordinate().getLatitude());
            System.err.println("MAP USER LIST " + u.getCoordinate().getLongitude() + "\n");
        }*/

        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.child("User").addChildEventListener(new ChildEventListener() {
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
        });
    }
}
