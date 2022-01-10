package com.example.fyp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.databinding.ActivityHistoryTicketDetailsBinding;
import com.example.fyp.databinding.ActivityPurchaseTicketDetailsBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
import static com.example.fyp.utility.Constant.INTENT_USERID;

public class activity_search_ticket_details extends AppCompatActivity {

    String ticketId, busPlateNumber, toLocation, fromLocation, departureTime,departureDate, arrivedTime,arrivalDate , companyName,ticketPrice,ticketStage, userId;
    TextView tv_ticket_id, tv_bus_plate, tv_to_location, tv_from_location, tv_departure_time, tv_departure_date, tv_arrived_time, tv_arrived_date;
    TextView tv_company_name, tv_ticket_price, tv_ticket_stage;

    private ActivityPurchaseTicketDetailsBinding binding;
    private ToolbarViewBinding toolbar;

    private FirebaseDatabase db;
    private DatabaseReference mDatabase;

    SharedPreferences prefs;
    String uid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_ticket_details);

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
        userId = getIntent().getStringExtra(INTENT_USERID);

        binding = ActivityPurchaseTicketDetailsBinding.inflate(getLayoutInflater());
        toolbar = binding.includedToolbar;
        View view = binding.getRoot();
        setContentView(view);
        prefs = PreferenceManager.getDefaultSharedPreferences(activity_search_ticket_details.this);
        uid = prefs.getString("Uid", "defaultStringIfNothingFound");


        setupView();
        setToolbar();
        setupOnClick();

    }

    private void setToolbar() {
        toolbar.tvTitle.setText("Ticket Details");
        toolbar.ivRight.setImageResource(R.drawable.ic_trash);
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
    private void setupOnClick(){
        toolbar.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookingCreate();
            }
        });
    }

    private void onBookingCreate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ticketID", ticketId);
        hashMap.put("busPlateNumber", busPlateNumber);
        hashMap.put("toLocation", toLocation);
        hashMap.put("fromLocation", fromLocation);
        hashMap.put("departureTime", departureTime);
        hashMap.put("departureDate", departureDate);
        hashMap.put("arrivedTime", arrivedTime);
        hashMap.put("arrivedDate", arrivalDate);
        hashMap.put("companyName", companyName);
        hashMap.put("ticketPrice", ticketPrice);
        hashMap.put("stage",ticketStage);
        hashMap.put("bookingDate", date);


        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference("booking");
        mDatabase.child(uid).child(ticketId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity_search_ticket_details.this, "Success Book", Toast.LENGTH_LONG).show();
                onItemRemove();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_search_ticket_details.this, "Fail Add", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onItemRemove(){
        Toast.makeText(activity_search_ticket_details.this, "Hey", Toast.LENGTH_LONG).show();
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        Query query = dRef.child("ticket").child(userId).orderByChild("ticketID").equalTo(ticketId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(activity_search_ticket_details.this, "no", Toast.LENGTH_LONG).show();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Toast.makeText(activity_search_ticket_details.this, "yes", Toast.LENGTH_LONG).show();
                    dataSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
