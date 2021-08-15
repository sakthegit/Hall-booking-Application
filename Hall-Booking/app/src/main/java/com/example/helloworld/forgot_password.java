package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    FirebaseAuth fbauth;
    EditText et_id;
    Button btn_reset;
    TextView login;
    String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fbauth = FirebaseAuth.getInstance();
        et_id = findViewById(R.id.forgot_id);
        login = findViewById(R.id.forgot_staff_login);
        btn_reset = findViewById(R.id.btn_reset);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgot_password.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
                finish();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_id = et_id.getText().toString();
                if(str_id.length()>4){
                    fbauth.sendPasswordResetEmail(str_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(forgot_password.this,"Reset Mail has sent",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(forgot_password.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(forgot_password.this,"Enter the valid Email Id",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    et_id.requestFocus();
                    et_id.setError("Enter the Valid Email Id");
                }
            }
        });
    }
}