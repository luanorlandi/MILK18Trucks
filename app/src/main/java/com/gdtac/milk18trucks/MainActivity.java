package com.gdtac.milk18trucks;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import database.DatabaseRoot;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference rootRef;

    private MapsFragment mapsFragment;

    private GPSTracker gpsTracker = null;
    private MyUser myUser = null;

    private DatabaseRoot databaseRoot;
    private MarkerHandler markerHandler;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Firebase setup */
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user == null) {
            callSignActivity();
        }

        rootRef = FirebaseDatabase.getInstance().getReference();

        /* Navigator setup */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

        /* Fragment setup */
        fragmentManager = getSupportFragmentManager();
        mapsFragment = new MapsFragment();
        showFragment(mapsFragment, "MapsFragment");

        /* GPS tracker */
        //gpsTracker = new GPSTracker(this);
        requestGPS();   /* API 23 or above */

        /* database root */
        databaseRoot = new DatabaseRoot();
        mapsFragment.setDatabaseRoot(databaseRoot);

        /* marker handler */
        markerHandler = new MarkerHandler(databaseRoot, mapsFragment, this);
        new Thread(markerHandler).start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        gpsTracker.stopGPS();

        myUser.setEnabled(false);

        databaseRoot.stopFarmListener();
        databaseRoot.stopIndustryListener();
        databaseRoot.stopUserListener();

        markerHandler.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            case R.id.nav_about:
                Uri uri = Uri.parse("https://github.com/luanorlandi/MILK18Trucks#milk18trucks");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.nav_signout:
                myUser.setEnabled(false);
                mAuth.signOut();
                callSignActivity();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callSignActivity() {
        if(myUser != null) {
            myUser.setEnabled(false);
        }

        finish();
        startActivity(new Intent(this, SignActivity.class));
    }

    private void showFragment(Fragment fragment, String name) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.container, fragment, name);
        transaction.commitAllowingStateLoss();
    }

    private void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        TextView textViewName = (TextView)hView.findViewById(R.id.navHeaderName);
        TextView textViewEmail = (TextView)hView.findViewById(R.id.navHeaderEmail);
        ImageView imageViewEmail = (ImageView)hView.findViewById(R.id.navHeaderImage);

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        textViewEmail.setText(email);
    }

    private void requestGPS() {
        if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            this.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            startGPS();
        }
    }

    /* call after request for GPS in accepted */
    private void startGPS() {
        gpsTracker = new GPSTracker(this);

        /* start thread to update user location */
        myUser = new MyUser(rootRef.child("User"), user, gpsTracker);
        new Thread(myUser).start();

        /* enable location UI */
        if(mapsFragment != null) {
            mapsFragment.googleMapEnableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startGPS();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, getResources().getString(
                            R.string.gps_required), Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
