package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLogin extends AppCompatActivity {

    EditText username,password;
    Button login_button;
    TextView stafflogin,forgot_password;
    private FirebaseAuth fbauth;
    FirebaseFirestore db;
    String str_login_id,str_login_password,userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        init();
        stafflogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
                finish();
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this,forgot_password.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                fbauth.signOut();
                if (fbauth.getCurrentUser() == null) {
                    reference();
                    if (!str_login_id.isEmpty() && !str_login_password.isEmpty() && str_login_password.length() > 5) {
                        ProgressDialog loadingbar = new ProgressDialog(AdminLogin.this);
                        loadingbar.setTitle("Signing In");
                        loadingbar.setMessage("Please wait while we check your credentials");
                        loadingbar.setCanceledOnTouchOutside(false);
                        loadingbar.show();
                        fbauth.signInWithEmailAndPassword(str_login_id, str_login_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                userid = fbauth.getUid();
                                db.collection("admin").document(userid)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        if(documentSnapshot.exists()){
                                            Toast.makeText(AdminLogin.this, "User Admin ", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AdminLogin.this, AdminHome.class);
                                            loadingbar.dismiss();
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            loadingbar.dismiss();
                                            Toast.makeText(AdminLogin.this, "Not a Admin", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingbar.dismiss();
                                Toast.makeText(AdminLogin.this, "Check ID and Password ", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }


                }
            }
        });



    }
    public void init(){
        username = findViewById(R.id.admin_login_id);
        password = findViewById(R.id.admin_login_password);
        forgot_password = findViewById(R.id.admin_forgot_password);
        stafflogin = findViewById(R.id.admin_staff_login);
        login_button = findViewById(R.id.admin_login_button);

        db = FirebaseFirestore.getInstance();
        fbauth = FirebaseAuth.getInstance();
    }
    private void reference()
    {
        str_login_id=username.getText().toString();
        str_login_password = password.getText().toString();

        if(str_login_id.isEmpty()){
            username.setError("Enter Email ID");
            username.requestFocus();
        }
        else if(str_login_password.isEmpty()){
            password.setError("Enter the Password");
            password.requestFocus();
        }
        else if(str_login_password.length()<6){
            password.setError("Enter the Valid Password");
            password.requestFocus();
        }
    }
}