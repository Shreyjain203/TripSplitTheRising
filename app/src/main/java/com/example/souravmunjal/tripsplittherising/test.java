package com.example.shreyjain.tripsplittherising;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.common.ConnectionResult;
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

public class test extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    GoogleApiClient googleApiClient;
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 007;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) test.this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        signInButton=(SignInButton) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                 startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        Button button = (Button)findViewById(R.id.create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(test.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                Toast.makeText(this, "Google sign in failed" , Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        final String email=acct.getEmail();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Bitmap bm = null;
                            InputStream is = null;
                            BufferedInputStream bis = null;
                            String personName = user.getDisplayName();
                            String personPhotoUrl = user.getPhotoUrl().toString();

                            Intent i = new Intent(test.this,Group.class);
                            i.putExtra("username",user.getDisplayName());
                            i.putExtra("userphoto",personPhotoUrl);
                            i.putExtra("email",email);

                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }
}
