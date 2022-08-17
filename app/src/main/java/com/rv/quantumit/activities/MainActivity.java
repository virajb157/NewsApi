package com.rv.quantumit.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rv.quantumit.R;
import com.rv.quantumit.dbhandler.DatabaseHandler;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends AppCompatActivity {
    TextView text;
    Button btnLogin, btnSignup;
    DatabaseHandler databaseHandler;
    EditText etEmail, etPass;
    Button fbBtn;
    private CallbackManager mCallBackManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    LoginButton loginButton;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googlebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        googlebtn = findViewById(R.id.google);
        text = (TextView) findViewById(R.id.tvsignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.signup);
        etEmail = findViewById(R.id.logEmail);
        etPass = findViewById(R.id.logPass);
        fbBtn = findViewById(R.id.mainLogin);
        loginButton = findViewById(R.id.fb_btn);

        databaseHandler = new DatabaseHandler(this);

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallBackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookAuthentication","onSuccess"+loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FacebookAuthentication","onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookAuthentication","onError"+error);

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    updateUI(user);
                }
                else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null){
                    mFirebaseAuth.signOut();
                }
            }
        };


        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHandler.getdetails(etEmail.getText().toString(), etPass.getText().toString()) > 0){
                    Intent in = new Intent(getApplicationContext(),activity_news.class);
                    startActivity(in);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong ID or Password",Toast.LENGTH_LONG).show();
                }

            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_signUp.class);
                startActivity(intent);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_signUp.class);
                startActivity(intent);
            }
        });
    }

    private void signin() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Log.d("FacebookAuthentication","handleFacebookToken"+accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("FacebookAuthentication", "sign in with credential: successful");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    Log.d("FacebookAuthentication","sign in with credential: successful",task.getException());
                    Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallBackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}