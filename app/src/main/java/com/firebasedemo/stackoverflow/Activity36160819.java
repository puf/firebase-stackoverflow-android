package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * This class is a minimal answer for the question on:
 *
 * http://stackoverflow.com/questions/36160819/how-to-populate-recyclerview-with-data-which-has-id-xxxx-in-firebase-using-java
 *
 * Read the full question and answer on Stack Overflow for full context
 *
 */
public class Activity36160819 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView answersList = new ListView(this);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/36160819/answers");

        Query queryRef = ref.orderByChild("questionid").equalTo("-KDi2YLc2nzdvJXvntYC");
        FirebaseListAdapter<DataSnapshot> adapter = new FirebaseListAdapter<DataSnapshot>(this, DataSnapshot.class,
                android.R.layout.two_line_list_item, queryRef) {
            @Override
            protected DataSnapshot parseSnapshot(DataSnapshot snapshot) {
                return snapshot;
            }

            @Override
            protected void populateView (View v, DataSnapshot answer, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(answer.child("authorid").getValue().toString());
                ((TextView)v.findViewById(android.R.id.text2)).setText(answer.child("answer").getValue().toString());
            }
        };
        answersList.setAdapter(adapter);

        setContentView(answersList);
    }
}
