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

public class user_edit_profile extends AppCompatActivity {

    private EditText name, ic, email, phone;
    private Button save;

    private FirebaseDatabase db;
    private DatabaseReference dbref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        name = findViewById(R.id.ed_name);
        ic = findViewById(R.id.ed_ic);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("Users");

        name.setText(getIntent().getStringExtra("user_name"));
        ic.setText(getIntent().getStringExtra("user_ic"));
        email.setText(getIntent().getStringExtra("user_email"));
        phone.setText(getIntent().getStringExtra("user_phone"));

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().length()!=0 && ic.getText().length()!=0 && email.getText().length()!=0 && phone.getText().length()!=0) {
                            String uid = auth.getUid();
                            String name2 = name.getText().toString();
                            String ic2 = ic.getText().toString();
                            //String email2 = email.getText().toString();
                            String phone2 = phone.getText().toString();

                            dbref.child(uid).child("name").setValue(name2);
                            dbref.child(uid).child("phone").setValue(phone2);
                            dbref.child(uid).child("ic").setValue(ic2);
                            //dbref.child(uid).child("email").setValue(email2);

                            Toast.makeText(user_edit_profile.this, "Profile Update Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent2main = new Intent(user_edit_profile.this, user_page.class);
                            startActivity(intent2main);
                        } else {
                            Toast.makeText(user_edit_profile.this, "All field must be filled!", Toast.LENGTH_SHORT).show();
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