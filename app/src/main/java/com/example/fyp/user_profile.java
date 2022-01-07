package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_profile extends AppCompatActivity {

    private EditText name1, ic1, email1, phone1;
    private Button save;

    private FirebaseDatabase db;
    private DatabaseReference dbref;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name1 = findViewById(R.id.et_name);
        ic1 = findViewById(R.id.et_ic);
        email1 = findViewById(R.id.et_email);
        phone1 = findViewById(R.id.et_phone);
        save = findViewById(R.id.bt_save);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("Users");

        //name1.setText(getIntent().getStringExtra("name"));
        //ic1.setText(getIntent().getStringExtra("ic"));
        //email1.setText(getIntent().getStringExtra("email"));
        //phone1.setText(getIntent().getStringExtra("phone"));

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String names = snapshot.child("user_name").getValue().toString();
                String emails = snapshot.child("user_email").getValue().toString();
                String phones = snapshot.child("user_phone").getValue().toString();
                String ics = snapshot.child("user_ic").getValue().toString();

                name1.setText(names);
                phone1.setText(phones);
                email1.setText(emails);
                ic1.setText(ics);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name1.getText().length()!=0 && ic1.getText().length()!=0 && email1.getText().length()!=0 && phone1.getText().length()!=0) {
                            String uid = auth.getUid();
                            String name2 = name1.getText().toString();
                            String ic2 = ic1.getText().toString();
                            String email2 = email1.getText().toString();
                            String phone2 = phone1.getText().toString();

                            dbref.child(uid).child("name").setValue(name2);
                            dbref.child(uid).child("phone").setValue(phone2);
                            dbref.child(uid).child("ic").setValue(ic2);
                            dbref.child(uid).child("email").setValue(email2);

                            Toast.makeText(user_profile.this, "Profile Update Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent2main = new Intent(user_profile.this, user_page.class);
                            startActivity(intent2main);
                        } else {
                            Toast.makeText(user_profile.this, "All field must be filled!", Toast.LENGTH_SHORT).show();
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