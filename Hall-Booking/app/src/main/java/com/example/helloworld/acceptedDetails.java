package com.example.helloworld;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class acceptedDetails extends AppCompatActivity {
    String deptname;
    private AcceptAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_details);

        FirebaseFirestore fbstore = FirebaseFirestore.getInstance();
        deptname = getIntent().getStringExtra("dept");
        CollectionReference acceptref = fbstore.collection(deptname + "Accept");
        RecyclerView rv = findViewById(R.id.rv_accept);


        Query query = acceptref.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<request_fetch> options = new FirestoreRecyclerOptions.Builder<request_fetch>()
                .setQuery(query, request_fetch.class)
                .build();
        adapter = new AcceptAdapter(options);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
