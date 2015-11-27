package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

/// Demo for http://stackoverflow.com/questions/33955548/firebase-cant-use-setvalue-on-lhs-of-assignment
public class Activity33955548 extends AppCompatActivity {
    public static class User {
        private int birthYear;
        private String fullName;

        public User() {
        }

        public User(String fullName, int birthYear) {
            this.fullName = fullName;
            this.birthYear = birthYear;
        }

        public long getBirthYear() {
            return birthYear;
        }

        public String getFullName() {
            return fullName;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33955548);

        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase("https://docs-examples.firebaseio.com/android/saving-data/fireblog");
        Firebase alanRef = ref.child("users").child("alanisawesome");
        User alan = new User("Alan Turing", 1912);
        alanRef.setValue(alan);
    }
}
