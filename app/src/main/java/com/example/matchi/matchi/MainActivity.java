package com.example.matchi.matchi;
import android.app.Activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import android.widget.Toast;

import com.facebook.*;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.x.y.android.R;

import org.json.JSONObject;


public class MainActivity extends Activity{
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public static final String mAPP_ID = "194243607597864";
    Facebook mFacebook= new Facebook(mAPP_ID);
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(!mFacebook.isSessionValid() ) {
                    mFacebook.authorize(LoginPage.this, new String[] {"publish_stream","email","user_groups","read_stream","user_about_me","offline_access"},Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
                }
                else {
                    try {
                        JSONObject json = Util.parseJson(mFacebook.request("me"));
                        profile.profileUser.setId(json.getString("id"));
                        profile.profileUser.setLName(json.getString("last_name"));
                        profile.profileUser.setFName(json.getString("first_name"));
                    }
                    catch (Exception e){
                        Toast.makeText( LoginActivity.this,"Exception FB "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    catch( FacebookError error ) {
                        Toast.makeText( LoginPage.this,error.toString(), Toast.LENGTH_SHORT).show();
                    }

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
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




}


