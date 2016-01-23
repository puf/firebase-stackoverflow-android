package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * This class is in support of the answer for the question on:
 *
 * http://stackoverflow.com/questions/34962254/how-to-retrieve-data-to-a-recycler-view-from-firebase
 *
 */

public class Activity34962254 extends AppCompatActivity {
    public static class Menu {
        String title;
        public String getTitle() { return title; }
    }
    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public MenuViewHolder(View itemView) {
            super(itemView);
        }
        public TextView getTitleView() {
            return (TextView) itemView;
        }
    }
    public static class MenuHomeAdapter extends RecyclerView.Adapter<MenuViewHolder> {
        ArrayList<Menu> items = new ArrayList<>();

        public MenuHomeAdapter(Firebase ref) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("Got snapshot with "+snapshot.getChildrenCount()+" children");
                    items.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Menu menu = postSnapshot.getValue(Menu.class);
                        items.add(menu);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        }

        @Override
        public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MenuViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuViewHolder holder, int position) {
            Menu item = items.get(position);
            holder.getTitleView().setText(item.getTitle());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_34962254);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/34962254");

        MenuHomeAdapter adapter = new MenuHomeAdapter(ref);

        RecyclerView menu = (RecyclerView) findViewById(R.id.recyclerview34962254);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        menu.setHasFixedSize(false);
        menu.setLayoutManager(manager);
        menu.setAdapter(adapter);
    }
}
