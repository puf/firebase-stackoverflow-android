package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;
import com.firebase.client.ValueEventListener;

public class Activity33260450 extends AppCompatActivity {

    public static class User {
        int age;
        int counter;

        public int getAge() {
            return age;
        }

        public int getCounter() {
            return counter;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }
    }

    Firebase ref;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33260450);

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);

        ref = new Firebase("https://millezim-test.firebaseIO.com").child("user");
        ref.keepSynced(true);

        Button br = (Button) findViewById(R.id.b_read);
        Button bs = (Button) findViewById(R.id.b_save);
        final TextView tv_r = (TextView) findViewById(R.id.tv_value_toread);
        final EditText tv_s = (EditText) findViewById(R.id.tv_value_tosave);

        user = new User();

        bs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tv_s.getText().toString().equalsIgnoreCase(""))
                    user.setAge(Integer.valueOf(tv_s.getText().toString()));
                ref.setValue(user);
            }
        });

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User u = dataSnapshot.getValue(User.class);
                        if (u != null)
                            tv_r.setText(String.valueOf(u.getAge()));
                        else
                            tv_r.setText("Bad Value");
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        // Add a value listener that will update the counter every second or so
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                u.setCounter(u.getCounter() + 1);
                user = u;
                ref.setValue(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
