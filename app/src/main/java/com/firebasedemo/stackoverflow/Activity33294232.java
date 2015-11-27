package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;

public class Activity33294232 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33294232);

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);

        Firebase myFirebaseRef = new Firebase("https://futurister.firebaseio.com/");

        FeedMessage msg = new FeedMessage("Tom", "High five, if it works!");

        myFirebaseRef.push().setValue(msg, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                System.out.println(firebaseError+": "+firebase);
            }
        });
    }

    public static class FeedMessage
    {
        String message;
        String name;

        public FeedMessage() {
        }

        public FeedMessage(String name, String message) {
            this.message = message;
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public String getName() {
            return name;
        }
    }
}
