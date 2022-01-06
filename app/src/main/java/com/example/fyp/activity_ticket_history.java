package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fyp.adapter.AdapterOwnTicket;
import com.example.fyp.model.TicketModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class activity_ticket_history extends AppCompatActivity {

    private RecyclerView recyclerViewOwnTicket;
    private DatabaseReference databaseReference;
    private ArrayList<TicketModel> ticketModelArrayList = new ArrayList<>();
    private AdapterOwnTicket adapterOwnTicket;
    private FloatingActionButton fabAddTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);

        for (int i = 0; i < 5; i++) {
            TicketModel ticket = new TicketModel();
            ticket.setTicketID("sgsggs");
            ticket.setCompanyName("cepatExpress");
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
    }
    void setupView() {
        recyclerViewOwnTicket = findViewById(R.id.recyclerViewOwnTicket);
        fabAddTicket = findViewById(R.id.fab);
    }

    void setupAdapter() {
        recyclerViewOwnTicket.setHasFixedSize(true);
        recyclerViewOwnTicket.setLayoutManager(new LinearLayoutManager(this));

        adapterOwnTicket = new AdapterOwnTicket(this, ticketModelArrayList);
        recyclerViewOwnTicket.setAdapter(adapterOwnTicket);
    }

    void setupNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.purchase);
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
}