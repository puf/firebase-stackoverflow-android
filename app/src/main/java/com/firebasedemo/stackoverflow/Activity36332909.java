package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.List;

/**
 * This class is a minimal answer for the question on:
 *
 * http://stackoverflow.com/questions/36332909/how-to-handle-situation-when-some-value-return-null-from-firebase
 *
 * Read the full question and answer on Stack Overflow for full context
 *
 */
public class Activity36332909 extends AppCompatActivity {
    public static class Comment {
        public String text;

        @Override
        public String toString() {
            return text;
        }
    }
    public static class Post {
        private String title;
        private long date;
        private List<Comment> comments;

        public String getTitle() {
            return title;
        }

        public long getDate() {
            return date;
        }

        public List<Comment> getComments() {
            return comments;
        }

        @Override
        public String toString() {
            return "title="+title+" date="+date+" comments="+(comments!=null?comments.toString():"<null>");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView view = new TextView(this);
        setContentView(view);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/36332909");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                view.append(dataSnapshot.getValue(Post.class).toString()+"\n");
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            public void onCancelled(FirebaseError firebaseError) { }
        });
    }
}
