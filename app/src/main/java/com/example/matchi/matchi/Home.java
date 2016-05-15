package com.example.matchi.matchi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//home page
public class Home extends Activity
{
    String[] userArray = {"Amy","Matt", "Andrei", "Ian", "George","Max","Tom"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        TextView facebookName = (TextView)findViewById(R.id.facebookName);
        String name = MainActivity.user.getName();
        facebookName.setText(name);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, userArray);
        ListView listView = (ListView)findViewById(R.id.user_list);
        listView.setAdapter(adapter);
        //open profile page when button clicked
        openProfilePage();



    }
    public void openProfilePage()
    {
        final Context context = this;
        Button profileButton = (Button)findViewById(R.id.profile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Profile.class);
                startActivity(intent);

            }
        });




    }


}
