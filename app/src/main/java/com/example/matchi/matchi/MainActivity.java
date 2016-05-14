package com.example.matchi.matchi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends Activity{
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Profile profile;
    private AccessToken access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                                profile.profileUser.setId(jsonObject.getString("id"));
                                profile.profileUser.setLName(jsonObject.getString("last_name"));
                                profile.profileUser.setFName(jsonObject.getString("first_name"));
                            }
                            catch (org.json.JSONException e){
                            }
                        }
                    })
                );
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
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




}


