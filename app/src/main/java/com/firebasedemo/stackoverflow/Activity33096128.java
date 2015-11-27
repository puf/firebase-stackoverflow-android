package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Activity33096128 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33096128);

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);

        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/33096128");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Thing thing = dataSnapshot.getValue(Thing.class);
                ((TextView) Activity33096128.this.findViewById(R.id.textView)).setText(thing.getId());
                System.out.println("new Value: " + dataSnapshot.toString() + ": getCreationDate=" + thing.getCreationDate() + " creationDateLong=" + thing.getCreationDateLong());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Thing thing = new Thing("one");
        ref.setValue(thing, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("onComplete: "+dataSnapshot.getValue(Thing.class).getCreationDateLong());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        /*
        Map<String,Object> updates = new HashMap<>();
        updates.put("thing", new ObjectMapper().convertValue(thing, Map.class));
        ref.child("atomic").updateChildren(updates);
        */
    }

    public static class Thing {
        private String id;
        private Long creationDate;
        private Long lastUpdatedDate;

        public Thing() {

        }

        public Thing(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public java.util.Map<String, String> getCreationDate() {
            return ServerValue.TIMESTAMP;
        }

        public java.util.Map<String, String> getLastUpdatedDate() {
            return ServerValue.TIMESTAMP;
        }

        @JsonIgnore
        public Long getCreationDateLong() {
            return creationDate;
        }

        @JsonIgnore
        public Long getLastUpdatedDateLong() {
            return lastUpdatedDate;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
