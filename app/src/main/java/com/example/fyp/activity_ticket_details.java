package com.example.fyp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ActivityTicketDetailsBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.model.TicketModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import static com.example.fyp.utility.Constant.START_FOR_RESULT_EDIT_TICKET;
import static com.example.fyp.utility.Constant.START_FOR_RESULT_OWN_TICKET;

public class activity_ticket_details extends AppCompatActivity {


    String ticketId, busPlateNumber, toLocation, fromLocation, departureTime,departureDate, arrivedTime,arrivalDate , companyName,ticketPrice,ticketStage;
    TextView tv_ticket_id, tv_bus_plate, tv_to_location, tv_from_location, tv_departure_time, tv_departure_date, tv_arrived_time, tv_arrived_date;
    TextView tv_company_name, tv_ticket_price, tv_ticket_stage;
    Button btnEdit;

    private ActivityTicketDetailsBinding binding;
    private ToolbarViewBinding toolbar;

    private FirebaseDatabase db;
    private DatabaseReference mDatabase;

    RelativeLayout progressIndicator;

    SharedPreferences prefs;
    String uid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_details);

        prefs = PreferenceManager.getDefaultSharedPreferences(activity_ticket_details.this);
        uid = prefs.getString("Uid", "defaultStringIfNothingFound");

        ticketId = getIntent().getStringExtra(INTENT_TICKET_ID);
        busPlateNumber = getIntent().getStringExtra(INTENT_BUS_PLATE);
        toLocation = getIntent().getStringExtra(INTENT_TO_LOCATION);
        fromLocation = getIntent().getStringExtra(INTENT_FROM_LOCATION);
        departureTime = getIntent().getStringExtra(INTENT_DEPARTURE_TIME);
        departureDate = getIntent().getStringExtra(INTENT_DEPARTURE_DATE);
        arrivedTime = getIntent().getStringExtra(INTENT_ARRIVED_TIME);
        arrivalDate = getIntent().getStringExtra(INTENT_ARRIVED_DATE);
        companyName = getIntent().getStringExtra(INTENT_COMPANY_NAME);
        ticketPrice = getIntent().getStringExtra(INTENT_TICKET_PRICE);
        ticketStage = getIntent().getStringExtra(INTENT_STAGE);

        binding = ActivityTicketDetailsBinding.inflate(getLayoutInflater());
        toolbar = binding.includedToolbar;
        View view = binding.getRoot();
        setContentView(view);

        setupView();
        setupData();
        setToolbar();
        setupOnClick();

    }

    private void setToolbar() {
        toolbar.tvTitle.setText("Ticket Details");
        toolbar.ivRight.setImageResource(R.drawable.ic_trash);
        toolbar.rlRight.setVisibility(View.VISIBLE);
    }

    private void setupData(){

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        Query query = dRef.child("ticket").child(uid).orderByChild("ticketID").equalTo(ticketId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                  TicketModel ticket = dataSnapshot.getValue(TicketModel.class);

                  ticketId = ticket.getTicketID();
                  busPlateNumber = ticket.getBusPlateNumber();
                  toLocation = ticket.getToLocation();
                  fromLocation = ticket.getFromLocation();
                  departureDate = ticket.getDepartureDate();
                  departureTime = ticket.getDepartureTime();
                  arrivalDate = ticket.getArrivedDate();
                  arrivedTime = ticket.getArrivedTime();
                  companyName = ticket.getCompanyName();
                  ticketPrice = ticket.getTicketPrice();
                  ticketStage = ticket.getStage();

                    Log.d("ry", ticket.getTicketID());

                }

                setupTextView();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setupTextView(){
        Log.d("enter", ticketId);
        tv_ticket_id.setText(ticketId);
        tv_bus_plate.setText(busPlateNumber);
        tv_to_location.setText(toLocation);
        tv_from_location.setText(fromLocation);
        tv_departure_date.setText(departureTime + " " + departureDate);
        tv_arrived_date.setText(arrivedTime + " " + arrivalDate);
        tv_company_name.setText(companyName);
        tv_ticket_price.setText(ticketPrice);
        tv_ticket_stage.setText(ticketStage);
    }

    private void setupView() {
        tv_ticket_id = findViewById(R.id.tv_ticket_id);
        tv_bus_plate = findViewById(R.id.tv_bus_plate);
        tv_to_location = findViewById(R.id.tv_to_location);
        tv_from_location = findViewById(R.id.tv_from_location);
        tv_departure_date = findViewById(R.id.tv_departure_date);
        tv_arrived_date = findViewById(R.id.tv_arrived_time);
        tv_company_name = findViewById(R.id.tv_company_name);
        tv_ticket_price = findViewById(R.id.tv_ticket_price);
        tv_ticket_stage = findViewById(R.id.tv_ticket_stage);
        btnEdit = findViewById(R.id.btn_edit);
        progressIndicator = findViewById(R.id.progress_indicator);


        mDatabase = FirebaseDatabase.getInstance().getReference();


    }
    private void setupOnClick(){
        toolbar.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_ticket_details.this, activity_user_edit_ticket.class);
                intent.putExtra(INTENT_TICKET_ID,ticketId);
                intent.putExtra(INTENT_BUS_PLATE,busPlateNumber);
                intent.putExtra(INTENT_TO_LOCATION,toLocation);
                intent.putExtra(INTENT_FROM_LOCATION,fromLocation);
                intent.putExtra(INTENT_DEPARTURE_TIME,departureTime);
                intent.putExtra(INTENT_DEPARTURE_DATE,departureDate);
                intent.putExtra(INTENT_ARRIVED_TIME,arrivedTime);
                intent.putExtra(INTENT_ARRIVED_DATE,arrivalDate);
                intent.putExtra(INTENT_COMPANY_NAME,companyName);
                intent.putExtra(INTENT_TICKET_PRICE,ticketPrice);
                intent.putExtra(INTENT_STAGE,ticketStage);

                startActivityForResult(intent,START_FOR_RESULT_EDIT_TICKET);
            }
        });

        toolbar.rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity_ticket_details.this)
                        .setTitle(getString(R.string.dialog_delete_ask_title))
                        .setMessage(getString(R.string.dialog_delete_ask_user))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                progressIndicator.setVisibility(View.VISIBLE);
                                deleteTicket();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void deleteTicket() {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        Query query = dRef.child("ticket").child(uid).orderByChild("ticketID").equalTo(ticketId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressIndicator.setVisibility(View.GONE);
                            AlertDialog alertDialog = new AlertDialog.Builder(activity_ticket_details.this)
                                    .setTitle(getString(R.string.dialog_delete_ask_user))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            Intent intent = new Intent();
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    }).show();

                            Toast.makeText(activity_ticket_details.this, "User is deleted:", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }




        });
        progressIndicator.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == START_FOR_RESULT_EDIT_TICKET  ){
            Log.e("124", "call back" );
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(activity_ticket_details.this, "edit ticket", Toast.LENGTH_LONG).show();
                setupData();
            }
        }
    }

}
