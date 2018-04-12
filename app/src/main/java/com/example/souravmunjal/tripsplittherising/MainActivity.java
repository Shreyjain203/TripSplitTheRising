package com.example.shreyjain.tripsplittherising;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button create;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    GoogleApiClient googleApiClient;
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 007;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_test);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Intent intent = new Intent(Mainactivity1.this, authenticatp.class);

                Intent intent = new Intent(MainActivity.this, com.example.shreyjain.tripsplittherising.test.class);

                //Intent intent = new Intent(Mainactivity1.this, Search2.class);

                startActivity(intent);

                finish();
            }

        }, SPLASH_TIME_OUT);
    }

}
