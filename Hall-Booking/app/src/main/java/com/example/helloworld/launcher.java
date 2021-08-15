package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class launcher extends AppCompatActivity {
    FirebaseAuth fbauth;
    FirebaseUser currentuser;
    FirebaseFirestore fbstore;

    private Handler mWaitHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    fbstore = FirebaseFirestore.getInstance();
                    fbauth = FirebaseAuth.getInstance();
                    currentuser = fbauth.getCurrentUser();
                    if (currentuser != null) {
                        fbstore.collection("admin").document(fbauth.getUid().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){

                                Intent intent = new Intent(launcher.this, AdminHome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                                startActivity(intent);
                                finish();}
                                else{
                                    Intent intent = new Intent(launcher.this, Department.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                    else{
                        Intent intent = new Intent(launcher.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent);
                        finish();
                    }
                }
                catch(Exception ignored){
                    Toast.makeText(launcher.this, ignored.toString(), Toast.LENGTH_LONG).show();
                    ignored.printStackTrace();
                    }
                }
            },1500);
        }
    }