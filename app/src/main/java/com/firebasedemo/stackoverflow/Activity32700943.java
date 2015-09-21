package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * This class is a minimal test case for the question on:
 *
 * http://stackoverflow.com/questions/32700943/datasnapshot-exists-firebase-returning-non-existent-values
 *
 */
public class Activity32700943 extends AppCompatActivity {
    public static String TAG = "Activity32700943";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_32700943);

        Firebase.setAndroidContext(this);

        checkPhonenumberLeadIdIndex("uid:12731287", "42", "+31632476423768");
    }

    public void log(String msg) {
        Log.d(TAG, msg);
        TextView log = (TextView) findViewById(R.id.text32700943);
        log.setText(log.getText() + msg + "\n");
    }

    public void checkPhonenumberLeadIdIndex(final String userId, final String projectId, final String phoneNumber)
    {
        Firebase fb = new Firebase("https://stackoverflow.firebaseio.com/32700943/users/" + userId
                + "/projects/" + projectId + "/leads/");
        Firebase newfb = fb.child(phoneNumber);

        newfb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    log(dataSnapshot.getKey() + ":" + dataSnapshot.getValue().toString());

                } else {
                    log("no key Calling callback ->");
                    saveLeadCallback(userId, projectId, phoneNumber);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void saveLeadCallback(String userId, String projectId, String phoneNumber) {
        Firebase fb = new Firebase("https://stackoverflow.firebaseio.com/32700943/users/" + userId
                + "/projects/" + projectId + "/leads/");
        Firebase newfb = fb.child(phoneNumber);
        newfb.setValue("Hello");

    }
}
