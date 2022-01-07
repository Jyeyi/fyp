package com.example.fyp;

import android.os.Bundle;

import com.example.fyp.model.TicketModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

class activity_ticket_details extends AppCompatActivity {


    String ticketId, busPlateNumber, toLocation, fromLocation, departureTime,arrivedTime, companyName,ticketPrice;
    boolean adultTicket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket_details);


    }
}
