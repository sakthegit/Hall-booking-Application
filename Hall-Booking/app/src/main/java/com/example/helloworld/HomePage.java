package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class HomePage extends Activity {


    private RecyclerView rv;
    private String dept_name,period,period_time,date;
    private FirebaseDatabase fbdata;
    private DatabaseReference dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fbdata = FirebaseDatabase.getInstance();

        dept_name = getIntent().getStringExtra("dept_name");
        date = getIntent().getStringExtra("date");
        period = getIntent().getStringExtra("period");
        period_time = getIntent().getStringExtra("period_time");

        dataref = fbdata.getReference().child(dept_name);
        rv = findViewById(R.id.rc_view);
        dataref.keepSynced(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<list_data,HomePage.homepageViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<list_data, homepageViewHolder>
                (list_data.class,R.layout.list_data,HomePage.homepageViewHolder.class,dataref) {
            @Override
            protected void populateViewHolder(HomePage.homepageViewHolder homepageViewHolder, list_data list_data,final int i) {
                homepageViewHolder.setVenue(list_data.getVenue());



                homepageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key_value = getRef(i).getKey();
                        DatabaseReference venue_clicked = getRef(i);
                        Query dataquery = dataref.child(key_value);

                        venue_clicked.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String clicked = (String) snapshot.child("venue").getValue();


                                Intent intent= new Intent(HomePage.this,Request.class);
                                intent.putExtra("venue",clicked);
                                intent.putExtra("dept_name",dept_name);
                                intent.putExtra("period",period);
                                intent.putExtra("period_time",period_time);
                                intent.putExtra("date",date);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        };
        rv.setAdapter(firebaseRecyclerAdapter);
    }
    public static class homepageViewHolder extends RecyclerView.ViewHolder{

        public  View eview;
        public homepageViewHolder(@NonNull View itemView) {
            super(itemView);
            eview=itemView;
        }
        public void setVenue(String Venue){
            TextView textView = eview.findViewById(R.id.venuename);
            textView.setText(Venue);
        }
    }

}


