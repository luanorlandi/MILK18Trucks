package com.gdtac.milk18trucks;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userName;
    private TextView userEmail;
    private TextView userUid;
    private Button signOutButton;

    private EditText coordX;
    private EditText coordY;
    private Button buttonUpdate;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (TextView) findViewById(R.id.userNameText);
        userEmail = (TextView) findViewById(R.id.userEmailText);
        userUid = (TextView) findViewById(R.id.userUidText);
        signOutButton = (Button) findViewById(R.id.signOut);

        coordX = (EditText) findViewById(R.id.coordX);
        coordY = (EditText) findViewById(R.id.coordY);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null) {
            finish();
            startActivity(new Intent(this, SignActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Name, email address, and profile photo Url
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getToken() instead.
        String uid = user.getUid();

        userName.setText("Nome: " + name);
        userEmail.setText("E-mail: " + email);
        userUid.setText("ID: " + uid);

        signOutButton.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == signOutButton) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, SignActivity.class));
        } else if(v == buttonUpdate) {
            updateCoord();
        }
    }

    private void updateCoord() {
        float x = Float.parseFloat(coordX.getText().toString());
        float y = Float.parseFloat(coordY.getText().toString());

        CoordenateInformation ci = new CoordenateInformation(x, y);

        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(ci);
    }
}
