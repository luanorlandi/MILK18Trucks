package com.gdtac.milk18trucks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailText;
    private EditText passwordText;
    private Button registerButton;
    private Button loginButton;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.register);
        loginButton = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            private String TAG = "\tFIREBASE ";

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    callMainActivity();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        progressDialog.setMessage(getResources().getString(R.string.wait_sign_up));
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private String TAG = "\tFIREBASE: ";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignActivity.this, getResources().getString(R.string.error_sign_up),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            callMainActivity();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    private void signIn(String email, String password) {
        progressDialog.setMessage(getResources().getString(R.string.wait_sign_in));
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    private String TAG = "\tFIREBASE: ";

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(SignActivity.this, getResources().getString(R.string.error_sign_in),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            callMainActivity();
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == registerButton) {
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this,
                        getResources().getString(R.string.error_email_required), Toast.LENGTH_SHORT).show();
                return;
            } else if(TextUtils.isEmpty(password)) {
                Toast.makeText(this,
                        getResources().getString(R.string.error_password_required), Toast.LENGTH_SHORT).show();
                return;
            }

            createAccount(emailText.getText().toString().trim(),
                    passwordText.getText().toString().trim());
        } else if(v == loginButton) {
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this,
                        getResources().getString(R.string.error_email_required), Toast.LENGTH_SHORT).show();
                return;
            } else if(TextUtils.isEmpty(password)) {
                Toast.makeText(this,
                        getResources().getString(R.string.error_password_required), Toast.LENGTH_SHORT).show();
                return;
            }

            signIn(emailText.getText().toString().trim(),
                    passwordText.getText().toString().trim());
        }
    }

    private void callMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
