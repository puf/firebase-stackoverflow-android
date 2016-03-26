package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.firebase.ui.FirebaseRecyclerAdapter;


/**
 * This class is a minimal test case for the question on:
 *
 * http://stackoverflow.com/questions/36235919/how-to-use-a-firebaserecycleradapter-with-a-dynamic-reference-in-android
 *
 */
public class Activity36235919 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_36235919);

        RecyclerView friendsList = (RecyclerView) findViewById(R.id.recyclerview36235919);

        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/36235919");

        final Firebase friendsRef = ref.child("users/user3/friends");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        friendsList.setHasFixedSize(false);
        friendsList.setLayoutManager(layoutManager);

        FirebaseRecyclerAdapter<Boolean, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Boolean, ItemViewHolder>(
                Boolean.class, android.R.layout.two_line_list_item, ItemViewHolder.class, friendsRef){
            protected void populateViewHolder(final ItemViewHolder viewHolder, Boolean model, int position) {
                String key = this.getRef(position).getKey();
                ref.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        ((TextView)viewHolder.itemView.findViewById(android.R.id.text1)).setText(name);
                    }

                    public void onCancelled(FirebaseError firebaseError) { }
                });
            }
        };

        friendsList.setAdapter(adapter);
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
