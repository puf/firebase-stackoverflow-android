package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;import android.widget.Adapter;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.Map;

/**
 * This class is a minimal answer for the question on:
 *
 * http://stackoverflow.com/questions/34559171/coupling-firebaserecyclerviewadapter-to-a-boolean-string-map-entry
 *
 */
public class Activity34559171 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_34559171);

        Firebase.setAndroidContext(this);

        final Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/34559171");

        FirebaseRecyclerAdapter<Boolean, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Boolean, ItemViewHolder>(Boolean.class, android.R.layout.two_line_list_item, ItemViewHolder.class, ref.child("index")){
            protected void populateViewHolder(final ItemViewHolder viewHolder, Boolean model, int position) {
                String key = this.getRef(position).getKey();
                ref.child("items").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String date = dataSnapshot.getValue(String.class);
                        ((TextView)viewHolder.itemView.findViewById(android.R.id.text1)).setText(date);
                    }

                    public void onCancelled(FirebaseError firebaseError) { }
                });
            }
        };
        RecyclerView dates = (RecyclerView) findViewById(R.id.recyclerview34559171);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        dates.setHasFixedSize(false);
        dates.setLayoutManager(manager);
        dates.setAdapter(adapter);

    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
