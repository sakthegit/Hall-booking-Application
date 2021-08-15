package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Request extends AppCompatActivity {

    private String department_name,venue,period_time,period,name,date,mail;
    private TextView tv_name,tv_dept,tv_venue,tv_period_time,tv_period,tv_date;
    private Button btn_apply;
    private ProgressDialog pd;
    private FirebaseFirestore db;
    private FirebaseAuth dbauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        department_name = getIntent().getStringExtra("dept_name");
        venue =  getIntent().getStringExtra("venue");
        period =  getIntent().getStringExtra("period");
        date = getIntent().getStringExtra("date");
        period_time = getIntent().getStringExtra("period_time");

        pd = new ProgressDialog(this);
        pd.setMessage("Getting ready...");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();

        tv_dept = findViewById(R.id.department);
        tv_name = findViewById(R.id.staff_name);
        tv_venue = findViewById(R.id.venue);
        tv_period = findViewById(R.id.period);
        tv_date = findViewById(R.id.date);
        tv_period_time = findViewById(R.id.time_period);
        btn_apply = findViewById(R.id.btn_apply);


        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        db = FirebaseFirestore.getInstance();
        dbauth = FirebaseAuth.getInstance();
        String uid = dbauth.getUid().toString();

        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        name = documentSnapshot.getString("name");
                        mail = documentSnapshot.getString("email");
                        init();
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection(department_name+"Accept").document(date+period_time+venue)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(!documentSnapshot.exists()) {
                            Toast.makeText(Request.this, "Request sent", Toast.LENGTH_SHORT).show();
                            DatabaseReference fbdataref = FirebaseDatabase.getInstance().getReference(department_name+"-REQUEST");
                            request_fetch rf = new request_fetch(name,department_name,date,venue,period_time,mail);
                            fbdataref.child(date+" "+period_time+" "+venue+" "+name).setValue(rf);
                            Toast.makeText(Request.this,"Request sent Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Request.this,Department.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Request.this, "Already Booked", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Request.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });
    }

    private void init() {
        tv_name.setText(name);
        tv_name.setSelected(true);
        tv_period_time.setText(period_time);
        tv_period_time.setSelected(true);
        tv_venue.setText(venue);;
        tv_venue.setSelected(true);
        tv_dept.setText(department_name);
        tv_period.setText(period);
        tv_date.setText(date);

    }
}