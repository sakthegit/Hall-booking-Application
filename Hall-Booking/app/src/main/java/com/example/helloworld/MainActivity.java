package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {
    String str_login_id,str_login_password;
    private FirebaseAuth fbauth;
    Button btn_login;
    ProgressDialog loadingbar;
    EditText login_id,login_password;
    TextView admin_login,forgot_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.login);
        fbauth = FirebaseAuth.getInstance();
        login_id = (EditText) findViewById(R.id.login_id);
        login_password= (EditText) findViewById(R.id.login_password);
        admin_login = findViewById(R.id.admin_login);

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AdminLogin.class));
            }
        });

        forgot_password = findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,forgot_password.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference();
                if(!str_login_id.isEmpty() && !str_login_password.isEmpty() && str_login_password.length()>5) {
                    loadingbar = new ProgressDialog(MainActivity.this);
                    loadingbar.setTitle("Signing In");
                    loadingbar.setMessage("Please wait while we check your credentials");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    fbauth.signInWithEmailAndPassword(str_login_id, str_login_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loadingbar.dismiss();
                            Intent intent = new Intent(MainActivity.this,Department.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingbar.dismiss();
                            Toast.makeText(MainActivity.this, "Check ID and Password ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
    private void reference()
    {
        str_login_id=login_id.getText().toString();
        str_login_password = login_password.getText().toString();

        if(str_login_id.isEmpty()){
            login_id.setError("Enter Email ID");
            login_id.requestFocus();
        }
        else if(str_login_password.isEmpty()){
            login_password.setError("Enter the Password");
            login_password.requestFocus();
        }
        else if(str_login_password.length()<6){
            login_password.setError("Enter the Valid Password");
            login_password.requestFocus();
    }
    }
}