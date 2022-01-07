package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ActivityUserMainBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class user_main extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference dbref;
    private FirebaseAuth auth;

    private CompositeDisposable compositeDisposable;
    private ToolbarViewBinding toolbar;
    private ActivityUserMainBinding binding;
    EditText etToLocation, etFromLocation;
    String selectedDate;
    final Calendar myCalendar= Calendar.getInstance();
    // date picker
    private static final int DATE_DEPARTURE = 1;
    private static final int DATE_ARRIVAL = 2;

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

        setOnClick();

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
}