package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Logger;

import java.util.Date;

/**
 * This class is in support of the answer for the question on:
 *
 * http://stackoverflow.com/questions/34634483/android-firebase-login-takes-a-bit-long-and-websocketexception-occurred
 *
 */
public class Activity34634483 extends AppCompatActivity {

    private static final String TAG = "Activity34634483";
    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjg2NTQ1Mjc4OTM1NywiZGVidWciOnRydWUsInYiOjAsImQiOnsidWlkIjoid2lzZW1hbm4ifSwiaWF0IjoxNDUyODc1NzU3fQ.cgBE6PjxmKjnSD_CHzr-SUW3yCkKMPK0xEtDwOf3tZo";
    private TextView mOutputTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_34634483);

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
        final Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/");

        mOutputTxt = (TextView) findViewById(R.id.outputTxt);
        Button authBtn = (Button) findViewById(R.id.authBtn);
        Button unauthBtn = (Button) findViewById(R.id.unauthBtn);

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("authBtn.click");
                ref.authWithCustomToken(TOKEN, null);
            }
        });
        unauthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("unauthBtn.click");
                ref.unauth();
            }
        });

        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                log("authStatChanged: authData="+authData);
            }
        });
    }

    private void log(String msg) {
        Log.i(TAG, msg);
        mOutputTxt.setText(mOutputTxt.getText() + new Date().toString()+": "+msg+"\n");
    }
}
