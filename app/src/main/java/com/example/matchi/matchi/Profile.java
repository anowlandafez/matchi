package com.example.matchi.matchi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

//profile page
public class Profile extends Activity
{
    Button button;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView facebookName = (TextView)findViewById(R.id.facebookName);
        String name = MainActivity.user.getName();
        facebookName.setText(name);
        openHomePage();
    }
    public void openHomePage() {
        final Context context = this;
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                startActivity(intent);

            }
        });
    }
}
