package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ActivityTicketDetailsBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.model.TicketModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.Nullable;
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

public class activity_ticket_details extends AppCompatActivity {


    String ticketId, busPlateNumber, toLocation, fromLocation, departureTime,departureDate, arrivedTime,arrivalDate , companyName,ticketPrice,ticketStage;
    TextView tv_ticket_id, tv_bus_plate, tv_to_location, tv_from_location, tv_departure_time, tv_departure_date, tv_arrived_time, tv_arrived_date;
    TextView tv_company_name, tv_ticket_price, tv_ticket_stage;
    Button btnEdit;

    private ActivityTicketDetailsBinding binding;
    private ToolbarViewBinding toolbar;


    private FirebaseDatabase db;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_details);

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
        setToolbar();
        setupOnClick();

    }

    private void setToolbar() {
        toolbar.tvTitle.setText("Ticket Details");
        toolbar.ivRight.setImageResource(R.drawable.ic_trash);
        toolbar.rlRight.setVisibility(View.VISIBLE);
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

//        mDatabase = FirebaseDatabase.getInstance().getReference();

        tv_ticket_id.setText(ticketId);
        tv_bus_plate.setText(ticketId);
        tv_to_location.setText(ticketId);
        tv_from_location.setText(ticketId);
        tv_departure_date.setText(departureDate);
        tv_arrived_date.setText(arrivalDate);
        tv_company_name.setText(companyName);
        tv_ticket_price.setText(ticketPrice);
        tv_ticket_stage.setText(ticketStage);

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
                startActivity(intent);
            }
        });

        toolbar.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

}
