package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class reset_password extends AppCompatActivity {

    private EditText email;
    private Button reset_pass;
    private TextView login, register;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.ed_email);
        reset_pass = findViewById(R.id.bt_reset_pass);
        login = findViewById(R.id.tx_login);
        register = findViewById(R.id.tx_register);

        auth = FirebaseAuth.getInstance();

        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();

                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(reset_password.this, "Please insert yout password",Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(reset_password.this, "The link has been send to your email, you can check your email now", Toast.LENGTH_SHORT).show();
                                Intent intent2login = new Intent(reset_password.this, MainActivity.class);
                                startActivity(intent2login);
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(reset_password.this, "Error" + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2login = new Intent(reset_password.this, MainActivity.class);
                startActivity(intent2login);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2register = new Intent(reset_password.this, register.class);
                startActivity(intent2register);
                finish();
            }
        });

    }
}