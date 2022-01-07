package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_profile extends AppCompatActivity {

    private EditText name, phone, ic, email;
    private Button save;

    private FirebaseAuth auth;
    private DatabaseReference dbref;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.et_name);
        phone = findViewById(R.id.et_phone);
        ic = findViewById(R.id.et_ic);
        email = findViewById(R.id.et_email);
        save = findViewById(R.id.bt_save);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("Users");

        name.setText(getIntent().getStringExtra("name").toString());
        phone.setText(getIntent().getStringExtra("phone").toString());
        ic.setText(getIntent().getStringExtra("ic").toString());
        email.setText(getIntent().getStringExtra("email").toString());

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().length()!=0 && phone.getText().length()!=0 && ic.getText().length()!=0 && email.getText().length()!=0){
                            String uid = auth.getUid();
                            String name_ = name.getText().toString();
                            String phone_ = phone.getText().toString();
                            String ic_ = ic.getText().toString();
                            String email_ = email.getText().toString();

                            dbref.child(uid).child("user_name").setValue(name_);
                            dbref.child(uid).child("user_phone").setValue(phone_);
                            dbref.child(uid).child("user_ic").setValue(ic_);
                            dbref.child(uid).child("user_email").setValue(email_);

                            Intent intent2main = new Intent(user_profile.this, user_page.class);
                            startActivity(intent2main);
                            Toast.makeText(user_profile.this,"Profile Update Successful!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(user_profile.this,"Fail to Update Profile!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}