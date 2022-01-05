package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class admin_user extends AppCompatActivity {

    private RecyclerView recyclerUser;
    private DatabaseReference db;
    private ArrayList arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
    }
}