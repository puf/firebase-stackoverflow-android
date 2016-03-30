package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;
import java.util.UUID;

/**
 * This class is a minimal test case for the question on:
 *
 * http://stackoverflow.com/questions/32319324/android-app-not-able-to-post-data-on-firebase
 *
 */
public class Activity32319324 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_32319324);

        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/32319324");

        // Ensure we start out with no data at this location
        ref.removeValue();

        // Monitor the location and output any data there to a text view
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                TextView output = (TextView) Activity32319324.this.findViewById(R.id.content_32319324);
                for (DataSnapshot project: snapshot.getChildren()) {
                    output.setText(
                        "key="+project.getKey() + "\n" +
                        "LogDate="+project.child("LogDate").getValue() + "\n" +
                        "ProjectId="+project.child("ProjectId").getValue()+"\n\n"
                    );
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Write data to the location
        String projectId = "42";
        String uniqueID = UUID.randomUUID().toString();
        String date = new Date().toString(); //Utility.getDateTime();

        Firebase jobchild = ref.child(projectId);
        jobchild.setValue(uniqueID);

        Firebase jimages = jobchild.child("ProjectId");
        jimages.setValue(projectId);

        Firebase jdate = jobchild.child("LogDate");
        jdate.setValue(date);
    }

}
