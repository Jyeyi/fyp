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

import static com.example.fyp.utility.Constant.INTENT_ARRIVED_DATE;
import static com.example.fyp.utility.Constant.INTENT_ARRIVED_TIME;
import static com.example.fyp.utility.Constant.INTENT_BUS_PLATE;
import static com.example.fyp.utility.Constant.INTENT_COMPANY_NAME;
import static com.example.fyp.utility.Constant.INTENT_DEPARTURE_DATE;
import static com.example.fyp.utility.Constant.INTENT_DEPARTURE_TIME;
import static com.example.fyp.utility.Constant.INTENT_FROM_LOCATION;
import static com.example.fyp.utility.Constant.INTENT_STAGE;
import static com.example.fyp.utility.Constant.INTENT_TICKET_ID;
import static com.example.fyp.utility.Constant.INTENT_TICKET_PRICE;
import static com.example.fyp.utility.Constant.INTENT_TO_LOCATION;
import static com.example.fyp.utility.Constant.START_FOR_RESULT_OWN_TICKET;


public  class activity_own_ticket extends AppCompatActivity implements AdapterOwnTicket.ItemClickListener{

    private RecyclerView recyclerViewOwnTicket;
    private DatabaseReference databaseReference;
    private ArrayList<TicketModel> ticketModelArrayList = new ArrayList<>();
    private AdapterOwnTicket adapterOwnTicket;
    private FloatingActionButton fabAddTicket;
    private ToolbarViewBinding toolbar;

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
            ticket.setDepartureDate("2PM 12/1/2022");
            ticket.setArrivedTime("2PM 12/1/2022");
            ticket.setArrivedDate("2PM 12/1/2022");
            ticket.setTicketPrice("RM 12");
            ticket.setStage("Adult");
            ticket.setBusPlateNumber("JUG1234");
            ticket.setTicketID("121212");
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
        Intent intent = new Intent(this, activity_ticket_details.class);
        intent.putExtra(INTENT_TICKET_ID,ticketModelArrayList.get(position).getTicketID());
        intent.putExtra(INTENT_BUS_PLATE,ticketModelArrayList.get(position).getBusPlateNumber());
        intent.putExtra(INTENT_TO_LOCATION,ticketModelArrayList.get(position).getToLocation());
        intent.putExtra(INTENT_FROM_LOCATION,ticketModelArrayList.get(position).getFromLocation());
        intent.putExtra(INTENT_DEPARTURE_TIME,ticketModelArrayList.get(position).getDepartureTime());
        intent.putExtra(INTENT_DEPARTURE_DATE,ticketModelArrayList.get(position).getDepartureDate());
        intent.putExtra(INTENT_ARRIVED_TIME,ticketModelArrayList.get(position).getArrivedTime());
        intent.putExtra(INTENT_ARRIVED_DATE,ticketModelArrayList.get(position).getArrivedDate());
        intent.putExtra(INTENT_COMPANY_NAME,ticketModelArrayList.get(position).getCompanyName());
        intent.putExtra(INTENT_TICKET_PRICE,ticketModelArrayList.get(position).getTicketPrice());
        intent.putExtra(INTENT_STAGE,ticketModelArrayList.get(position).getStage());

//        intent.putExtra()
//        intent.putExtra(INTENT_ID,tncArrayList.get(position).getId());
        startActivityForResult(intent,START_FOR_RESULT_OWN_TICKET);
        overridePendingTransition(0, 0);

    }
}