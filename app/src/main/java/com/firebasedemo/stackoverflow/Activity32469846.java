package com.firebasedemo.stackoverflow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class is a minimal test case for the question on:
 *
 * http://stackoverflow.com/questions/32469846/remove-a-single-node-on-a-click-on-the-button
 *
 */
public class Activity32469846 extends AppCompatActivity {

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_32469846);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://stackoverflow.firebaseio.com/32469846");

        Button DelRestNow = (Button) findViewById(R.id.delButton);
        final TextView jsonText = (TextView) findViewById(R.id.jsonText);
        Button ResetButton = (Button) findViewById(R.id.resetButton);

        DelRestNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DelData();
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetData();
            }
        });

        // Add a listener to display the JSON
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                jsonText.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public void DelData(){
        // GET and DELETE(Through Database Restaurant Name
        EditText mEdit = (EditText)findViewById(R.id.delText);
        final String RestName = mEdit.getText().toString();
        // Get from Database
        Query query = mRef.child("Breakfast/Name").orderByValue().equalTo(RestName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        itemSnapshot.getRef().removeValue();
                    }
                }
                else {
                    new AlertDialog.Builder(Activity32469846.this)
                            .setTitle("Name not found")
                            .setMessage("No entry found with name \"" + RestName + "\"")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // allow the dialog to close
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mEdit.setText("");
    }

    public void ResetData() {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};

        String json = "{\n" +
                " 'BibleVerse': 'Verse1',\n" +
                " 'Breakfast': {\n" +
                "  'Name': {\n" +
                "   '-JyjnX_b6yQCR068oA9i': 'firefly',\n" +
                "   '-Jyjn_5Wzr1vP5rXi_IG': 'congee queen',\n" +
                "   '-JyjncESMv5vd2nQh4l6': 'steakhouse'\n" +
                "  }\n" +
                " },\n" +
                " 'Dinner': 'Dinner',\n" +
                " 'Gathering': 'Home',\n" +
                " 'Lunch': 'Lunch',\n" +
                " 'NexcellMember': 'Member1',\n" +
                " 'User': 'Adrian'\n" +
                "}";
        json = json.replaceAll("'", "\"");

        HashMap<String,Object> map = null;
        try {
            map = mapper.readValue(json, typeRef);
            System.out.println("Got " + map);
            mRef.setValue(map);
        } catch (IOException e) {
            Log.e("Activity32469846", "Error deserializing JSON", e);
        }
    }

}
