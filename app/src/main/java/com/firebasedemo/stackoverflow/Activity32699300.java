package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * This class is a minimal test case for the question on:
 *
 * http://stackoverflow.com/questions/32699300/retrieving-a-firebase-key-starting-with-strips-the-sign
 *
 */
public class Activity32699300 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_32699300);

        final TextView output = (TextView) findViewById(R.id.text32699300);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/32699300/");
        Firebase phoneRef = ref.child("+31612345678");
        phoneRef.setValue("Hello");
        phoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                output.setText(dataSnapshot.getKey()+": "+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
