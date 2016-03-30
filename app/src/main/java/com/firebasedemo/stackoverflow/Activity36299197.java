package com.firebasedemo.stackoverflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is a minimal answer for the question on:
 *
 * http://stackoverflow.com/questions/36299197/data-is-not-getting-populated-using-firebaserecycleradapter
 *
 * Read the full question and answer on Stack Overflow for full context
 *
 */
public class Activity36299197 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView view = new RecyclerView(this);
        setContentView(view);

        view.setLayoutManager(new LinearLayoutManager(this));

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://stackoverflow.firebaseio.com/36299197").child("subscriptions/obama@gmsil,com");

        FirebaseRecyclerAdapter<Program, ProgramVH> adapter = new FirebaseRecyclerAdapter<Program, ProgramVH>(Program.class, android.R.layout.two_line_list_item, ProgramVH.class, ref) {
            @Override
            public void populateViewHolder(final ProgramVH programViewHolder, Program program, int position) {
                System.out.println("populateViewHolder for position "+position+" with program "+program);
                programViewHolder.title.setText(program.getTitle());
                programViewHolder.level.setText(""+program.getLength()); // coerce to string to prevent android.content.res.Resources$NotFoundException
            }
        };
        view.setAdapter(adapter);
    }

    public static class Program {
        private String title;

        private String goal;

        private String category;

        private int length;

        HashMap<String, ArrayList<String>> weeks;

        public Program() {
        }

        public Program(String title, String goal,  String category, int length, HashMap<String, ArrayList<String>> weeks) {
            this.title = title;
            this.goal = goal;
            this.category = category;
            this.length = length;
            this.weeks = weeks;
        }

        public String getTitle() {
            return title;
        }

        public String getGoal() {
            return goal;
        }

        public String getCategory() {
            return category;
        }
        public int getLength() {
            return length;
        }
        public HashMap<String, ArrayList<String>> getweeks() {
            return weeks;
        }
    }
    public static class ProgramVH extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView level;

        public ProgramVH(View view) {
            super(view);
            this.title = (TextView) view.findViewById(android.R.id.text1);
            this.level = (TextView) view.findViewById(android.R.id.text2);
        }
    }
}
