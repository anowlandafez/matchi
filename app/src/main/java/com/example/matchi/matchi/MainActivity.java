package com.example.matchi.matchi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Context;

import com.facebook.*;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity implements
        ConnectionCallbacks {


    private GoogleApiClient mGoogleApiClient;
    private android.location.Location mLastLocation;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Profile profile;
    private AccessToken access_token;
    public static User user = new User("Barry", "");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //GPS stuff
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        //Facebook Stuff
        final Context context = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                access_token = AccessToken.getCurrentAccessToken();
                GraphRequestBatch batch = new GraphRequestBatch(
                    GraphRequest.newMeRequest(access_token,new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                            try {
                                String facebookID = jsonObject.getString("id");
                                String facebookName = jsonObject.getString("name");
                                System.out.println(facebookID + facebookName);
                                user = new User(facebookName,facebookID);
                            }
                            catch (org.json.JSONException e){
                            }
                        }
                    })
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,link");


                batch.addCallback(new GraphRequestBatch.Callback() {
                    @Override
                    public void onBatchCompleted(GraphRequestBatch graphRequests) {}
                });
                batch.executeAsync();

                Intent intent = new Intent(context, Home.class);
                startActivity(intent);
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException e) {
            }
        });
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
        catch (SecurityException e){}
    }

    @Override
    public void onConnectionSuspended(int a){}

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


