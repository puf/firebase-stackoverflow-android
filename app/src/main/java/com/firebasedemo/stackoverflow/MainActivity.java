package com.firebasedemo.stackoverflow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<ActivityInfo> activityInfos = new ArrayList<>();
        List<String> activityNames = new ArrayList<>();

        try {
            ActivityInfo[] activities = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities;
            for (ActivityInfo activityInfo: activities) {
                if (!activityInfo.name.equals(this.getClass().getName())) {
                    activityInfos.add(activityInfo);
                    // Use the class name, since the label may not be set for empty activities
                    String name = activityInfo.name.substring(activityInfo.packageName.length()+1);
                    String title = activityInfo.loadLabel(getPackageManager()).toString();
                    if (!title.equals(name) && !title.equals(getString(R.string.app_name))) {
                        name += " - " + title;
                    }
                    activityNames.add(name);
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityNames);
            ListView list = (ListView) findViewById(R.id.activities_list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ActivityInfo activity = activityInfos.get(position);
                    try {
                        Class clazz = Class.forName(activity.name);
                        Intent intent = new Intent(MainActivity.this, clazz);
                        startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
