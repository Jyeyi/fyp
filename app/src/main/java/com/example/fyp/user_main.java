package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.disposables.CompositeDisposable;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.adapter.AdapterOwnTicket;
import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ActivityUserMainBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.model.TicketModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

public class user_main extends AppCompatActivity implements AdapterOwnTicket.ItemClickListener {

    private RecyclerView recyclerViewOwnTicket;
    private DatabaseReference databaseReference;
    private ArrayList<TicketModel> ticketModelArrayList = new ArrayList<>();
    private AdapterOwnTicket adapterOwnTicket;

    private ArrayList<String> uidList = new ArrayList<>();

    private CompositeDisposable compositeDisposable;
    private ToolbarViewBinding toolbar;
    private ActivityUserMainBinding binding;
    EditText etToLocation, etFromLocation;
    String selectedDate;
    final Calendar myCalendar= Calendar.getInstance();
    // date picker
    private static final int DATE_DEPARTURE = 1;
    private static final int DATE_ARRIVAL = 2;

    SharedPreferences prefs;
    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_user_main);
        binding = ActivityUserMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.purchase:
                        startActivity(new Intent(getApplicationContext(), activity_ticket_history.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ticket:
                        startActivity(new Intent(getApplicationContext(), activity_own_ticket.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), user_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), user_page.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        setupView();
        setupAdapter();
        setOnClick();
        setData();

        prefs = PreferenceManager.getDefaultSharedPreferences(user_main.this);
        uid = prefs.getString("Uid", "defaultStringIfNothingFound");

    }

    void setupView() {
        recyclerViewOwnTicket = findViewById(R.id.recyclerViewOwnTicket);

    }

    void setupAdapter() {
        recyclerViewOwnTicket.setHasFixedSize(true);
        recyclerViewOwnTicket.setLayoutManager(new LinearLayoutManager(this));

        adapterOwnTicket = new AdapterOwnTicket(this, ticketModelArrayList, this);
        recyclerViewOwnTicket.setAdapter(adapterOwnTicket);
    }


    private void setData(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("ticket");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("Array", dataSnapshot.getRef().getKey());
                        uidList.add(dataSnapshot.getRef().getKey());

//                        TicketModel ticket = dataSnapshot.getValue(TicketModel.class);
//                        ticketModelArrayList.add(ticket);
                    }

                    getList();
                   // adapterOwnTicket.notifyDataSetChanged();

//                    if (ticketModelArrayList.isEmpty()) {
//                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
//                    } else {
//                        getList();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });



    }

    private void getList(){
        for(int i = 0; i < uidList.size(); i++){
            Query query = databaseReference.child("ticket").child(uidList.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                TicketModel ticket = dataSnapshot.getValue(TicketModel.class);
                                ticketModelArrayList.add(ticket);

                        }
                      //  adapterOwnTicket.notifyDataSetChanged();

                        if (ticketModelArrayList.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("hello", String.valueOf(ticketModelArrayList.size()));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void setOnClick() {
        compositeDisposable = new CompositeDisposable();


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };


        binding.llDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(user_main.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("From:", binding.edtFromLocation.getText().toString());
                Log.d("To:", binding.edtToLocation.getText().toString());
                Log.d("Date:", binding.edtDepartureDate.getText().toString());
            }
        });


    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);

        binding.edtDepartureDate.setText(dateFormat.format(myCalendar.getTime()));

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, activity_search_ticket_details.class);
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
}