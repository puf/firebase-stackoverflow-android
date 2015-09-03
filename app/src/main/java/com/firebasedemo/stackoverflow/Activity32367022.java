package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Activity32367022 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_32367022);

        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/32367022/Eat/Name");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int x = 1;
                Map<Integer, Object> map = new HashMap<Integer, Object>();
                Integer[] values = new Integer[(int)snapshot.getChildrenCount()];
                for (DataSnapshot child : snapshot.getChildren()) {
                    String name = child.getValue().toString();
                    System.out.println(name);
                    map.put(x, name);
                    values[x-1] = child.getValue(Integer.class);
                    x = x + 1;
                }
                System.out.println(map);
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(Activity32367022.this, android.R.layout.simple_list_item_1, values);
                ((ListView)Activity32367022.this.findViewById(R.id.listView)).setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

}
