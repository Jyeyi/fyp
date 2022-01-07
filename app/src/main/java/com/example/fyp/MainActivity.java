package com.example.fyp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private RadioButton user, admin;
    private Button login;
    private TextView register, resetPassword;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_password);
        user = findViewById(R.id.rb_user);
        admin = findViewById(R.id.rb_admin);
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.tx_register);
        resetPassword = findViewById(R.id.tx_reset_pass);

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                prefEditor.putString("Uid", "J5Ap8JiXAwbIeUlsHhZ4ZvYGJvM2");
                prefEditor.commit();

//                Intent intent2register = new Intent(MainActivity.this, user_main.class);
//                startActivity(intent2register);
//                finish();
//                Toast.makeText(MainActivity.this, "Successfully login as a user.", Toast.LENGTH_SHORT).show();

                if(email.getText().toString().isEmpty()){
                    email.setError("Please fill in your email!");
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    password.setError("Please fill in your password!");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());
                        dbref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String user_type = snapshot.child("user_type").getValue().toString();
                                if (user_type.equals("User") && user.isChecked()){
                                    Intent intent2register = new Intent(MainActivity.this, user_main.class);
                                    startActivity(intent2register);
                                    finish();
                                    Toast.makeText(MainActivity.this, "Successfully login as a user.", Toast.LENGTH_SHORT).show();
                                }
                                else if (user_type.equals("Admin") && user.isChecked()){
                                    Intent intent2register = new Intent(MainActivity.this, user_main.class);
                                    startActivity(intent2register);
                                    finish();
                                    Toast.makeText(MainActivity.this, "Successfully login as a admin.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    finish();
                                    Toast.makeText(MainActivity.this, "Unsuccessfully login.", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2register = new Intent(MainActivity.this, register.class);
                startActivity(intent2register);
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2resetPass = new Intent(MainActivity.this, reset_password.class);
                startActivity(intent2resetPass);

            }
        });

    }
}