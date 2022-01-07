package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.fyp.adapter.AdapterOwnTicket;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.model.TicketModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public  class activity_own_ticket extends AppCompatActivity implements AdapterOwnTicket.ItemClickListener{

    private RecyclerView recyclerViewOwnTicket;
    private DatabaseReference databaseReference;
    private ArrayList<TicketModel> ticketModelArrayList = new ArrayList<>();
    private AdapterOwnTicket adapterOwnTicket;
    private FloatingActionButton fabAddTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_ticket);


        for (int i = 0; i < 10; i++) {
            TicketModel ticket = new TicketModel();
            ticket.setTicketID("sgsggs");
            if(i % 2 != 0){
                ticket.setCompanyName("cepatExpress");
            } else {
                ticket.setCompanyName("slowExpress");
            }
            ticket.setToLocation("chaah");
            ticket.setFromLocation("jb");
            ticket.setDepartureTime("2PM 12/1/2022");
            ticket.setTicketID("121212");
            ticket.setAdultTicket(true);
            ticketModelArrayList.add(ticket);
        }

        setupView();
        setupAdapter();
        setupNavigationBar();

        fabAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_own_ticket.this, activity_user_add_ticket.class);
                startActivity(intent);
            }
        });
    }



    void setupView() {
        recyclerViewOwnTicket = findViewById(R.id.recyclerViewOwnTicket);
        fabAddTicket = findViewById(R.id.fab);
    }

    void setupAdapter() {
        recyclerViewOwnTicket.setHasFixedSize(true);
        recyclerViewOwnTicket.setLayoutManager(new LinearLayoutManager(this));

        adapterOwnTicket = new AdapterOwnTicket(this, ticketModelArrayList, this);


        recyclerViewOwnTicket.setAdapter(adapterOwnTicket);
    }

    void setupNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.ticket);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.purchase:
                        startActivity(new Intent(getApplicationContext(), activity_ticket_history.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.ticket:
                        startActivity(new Intent(getApplicationContext(), activity_own_ticket.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), user_main.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), user_page.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Log.d("try", ticketModelArrayList.get(position).getCompanyName());
        System.out.print(ticketModelArrayList.get(position).getCompanyName());
    }
}