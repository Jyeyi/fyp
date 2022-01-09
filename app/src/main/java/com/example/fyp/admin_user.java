package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_user extends AppCompatActivity {

    private RecyclerView recyclerUser;
    private DatabaseReference db;
    private ArrayList arrayList;

    private ArrayList<user> AdminUser;
    private RecyclerView.Adapter adapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        recyclerUser = findViewById(R.id.recyclerview_user);
        db = FirebaseDatabase.getInstance().getReference("Users");

        recyclerUser.setHasFixedSize(true);
        recyclerUser.setLayoutManager(new LinearLayoutManager(this));

        AdminUser = new ArrayList<>();
        //adapterUser = new AdapterUserList(this,A);

        recyclerUser.setAdapter(adapterUser);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    user user = dataSnapshot.getValue(com.example.fyp.user.class);
                    AdminUser.add(user);
                }
                adapterUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.purchase:
                        startActivity(new Intent(getApplicationContext(), admin_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.complaint:
                        startActivity(new Intent(getApplicationContext(), test.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), user_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), admin_user.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}