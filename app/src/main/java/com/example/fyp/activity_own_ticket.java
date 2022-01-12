package com.example.fyp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fyp.adapter.AdapterOwnTicket;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.model.TicketModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import static com.example.fyp.utility.Constant.START_FOR_RESULT_ADD_TICKET;
import static com.example.fyp.utility.Constant.START_FOR_RESULT_OWN_TICKET;


public  class activity_own_ticket extends AppCompatActivity implements AdapterOwnTicket.ItemClickListener{

    private RecyclerView recyclerViewOwnTicket;
    private DatabaseReference databaseReference;
    private ArrayList<TicketModel> ticketModelArrayList = new ArrayList<>();
    private AdapterOwnTicket adapterOwnTicket;
    private FloatingActionButton fabAddTicket;
    private ToolbarViewBinding toolbar;
    SharedPreferences prefs;
    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_ticket);

        prefs = PreferenceManager.getDefaultSharedPreferences(activity_own_ticket.this);
        uid = prefs.getString("Uid", "defaultStringIfNothingFound");

        setupView();
        setupAdapter();
        setupNavigationBar();
        setupData();

        fabAddTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_own_ticket.this, activity_user_add_ticket.class);
                startActivityForResult(intent,START_FOR_RESULT_ADD_TICKET);
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

        startActivityForResult(intent,START_FOR_RESULT_OWN_TICKET);
        overridePendingTransition(0, 0);

    }

    private void setupData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("ticket").child(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TicketModel ticket = dataSnapshot.getValue(TicketModel.class);
                        ticketModelArrayList.add(ticket);
                    }
                    adapterOwnTicket.notifyDataSetChanged();

                    if (ticketModelArrayList.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == START_FOR_RESULT_OWN_TICKET  ){
            Log.e("124", "call back" );
            if(resultCode == Activity.RESULT_OK){
                ticketModelArrayList.clear();
                setupData();
            }
        } else if(requestCode == START_FOR_RESULT_ADD_TICKET){
            Log.e("124", "call back edit" );
            if(resultCode == Activity.RESULT_OK){
                Log.e("124", "call back edit ok " );


                ticketModelArrayList.clear();
                setupData();
            }
        }
    }
}