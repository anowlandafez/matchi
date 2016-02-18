package com.example.matchi.matchi;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity{
    String[] userArray = {"Amy","Matt", "Andrei", "Ian", "George"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, userArray);
        ListView listView = (ListView)findViewById(R.id.user_list);
        listView.setAdapter(adapter);



    }


}
