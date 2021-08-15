package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;

public class Period extends AppCompatActivity {

    private RecyclerView rv;

    private String dept_name;
    private ProgressDialog loadingbar;
    private FirebaseDatabase fbdata;
    private TextView date_display;
    private DatabaseReference dataref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);

        dept_name = getIntent().getStringExtra("dept_name");

        fbdata = FirebaseDatabase.getInstance();
        dataref = fbdata.getReference().child("Period");
        rv = findViewById(R.id.rc1_view);
        date_display = findViewById(R.id.date);
        dataref.keepSynced(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE,1);
        int m=now.get(Calendar.MONTH)+1;
        int d=now.get(Calendar.DATE) ;
        int y=now.get(Calendar.YEAR);
        date_display.setText(d+":"+m+":"+y+"[Tomorrow]");
        String date = d+":"+m+":"+y;

        FirebaseRecyclerAdapter<list_data_period,Period.periodViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<list_data_period, periodViewHolder>
                (list_data_period.class,R.layout.list_data,Period.periodViewHolder.class,dataref) {
            @Override
            protected void populateViewHolder(Period.periodViewHolder periodViewHolder, list_data_period list_data_period,final int i) {
                periodViewHolder.setPeriod(list_data_period.getPeriod());
                periodViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key_value = getRef(i).getKey();
                        DatabaseReference period_clicked = getRef(i);
                        Query dataquery = dataref.child(key_value);

                        period_clicked.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String period_clicked = (String) snapshot.child("period").getValue();
                                String per =String.valueOf(period_clicked.charAt(0));

                                Intent intent = new Intent(Period.this,HomePage.class);
                                intent.putExtra("dept_name",dept_name);
                                intent.putExtra("period",per);
                                intent.putExtra("period_time",period_clicked);
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
    public static class periodViewHolder extends RecyclerView.ViewHolder{

        public  View eview;
        public periodViewHolder(@NonNull View itemView) {
            super(itemView);
            eview=itemView;
        }
        public void setPeriod(String name){
            TextView textView = eview.findViewById(R.id.venuename);
            textView.setText(name);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.onStart();
    }
}