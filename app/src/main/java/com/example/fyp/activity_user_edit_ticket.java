package com.example.fyp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ActivityEditTicketBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.model.TicketModel;
import com.example.fyp.utility.CommonUtils;
import com.example.fyp.utility.CustomTextUtils;
import com.example.fyp.utility.DateUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

import static android.content.ContentValues.TAG;
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

public class activity_user_edit_ticket extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private ToolbarViewBinding toolbar;
    private ActivityEditTicketBinding binding;
    EditText etTicketId, etBusPlateNo, etToLocation, etFromLocation, etDepartureDate, etDepartureTime, etArriveTime, etArrivedDate;
    EditText etTicketPrice, etStage, etCompanyName;
    final Calendar myCalendar= Calendar.getInstance();
    String ticketId, busPlateNumber, toLocation, fromLocation, departureDate, departureTime, arrivedTime, arrivalDate, ticketPrice, ticketStage, companyName;
    // date picker
    private static final int DATE_DEPARTURE = 1;
    private static final int DATE_ARRIVAL = 2;

    private FirebaseDatabase db;
    private DatabaseReference mDatabase;

    SharedPreferences prefs;
    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTicketBinding.inflate(getLayoutInflater());
        toolbar = binding.includedToolbar;
        View view = binding.getRoot();
        setContentView(view);

        prefs = PreferenceManager.getDefaultSharedPreferences(activity_user_edit_ticket.this);
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

        initDetails();
        setToolbar();
        setOnClick();
        initDetails();
    }

    private void initDetails(){
        binding.edtTicketId.setText(ticketId);
        binding.edtCarPlate.setText(busPlateNumber);
        binding.edtToLocation.setText(toLocation);
        binding.edtFromLocation.setText(fromLocation);
        binding.editDepartureTime.setText(departureTime);
        binding.edtDepartureDate.setText(departureDate);
        binding.etArrivedTime.setText(arrivedTime);
        binding.edtArrivedDate.setText(arrivalDate);
        binding.edtCompanyName.setText(companyName);
        binding.edtTicketPrice.setText(ticketPrice);
        binding.edtTicketStage.setText(ticketStage);

    }

    private void setToolbar() {
        toolbar.tvTitle.setText("Edit Ticket");
        toolbar.ivRight.setImageResource(R.drawable.ic_trash);
    }

    private void setOnClick() {
        compositeDisposable = new CompositeDisposable();

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(DATE_DEPARTURE);
            }
        };

        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(DATE_ARRIVAL);
            }
        };

        toolbar.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        binding.llDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.print("test departure date");
                Log.d("1","test departure date");

                new DatePickerDialog(activity_user_edit_ticket.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                showDatePicker(DATE_DEPARTURE, binding.edtDepartureDate.getText().toString());
            }
        });

        binding.llArrivedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity_user_edit_ticket.this,date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.btnSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onEditClicked();
                    }
                }
        );

    }

    private void showDatePicker(int type, String input) {
        final Calendar today = Calendar.getInstance();
        final Calendar calendar = Calendar.getInstance();

        if (!CustomTextUtils.isEmpty(input)) {
            try {
                Date date = DateUtils.getDate(DateUtils.DATE_FORMAT_4, input);
                calendar.setTime(date);
            } catch (Exception e) {
                CommonUtils.printExceptionTrace(TAG, e);
            }
        }


        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getApplicationContext(),
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, selectedYear);
                        calendar1.set(Calendar.MONTH, selectedMonth);
                        calendar1.set(Calendar.DAY_OF_MONTH, selectedDay);

                        Date date = calendar1.getTime();

                        try {
                            String displayDate;
                            displayDate = DateUtils.getDateStr(DateUtils.DATE_FORMAT_4, date);

                            switch (type) {
                                case DATE_DEPARTURE:
                                    binding.edtDepartureDate.setText(displayDate);
                                    break;
                                case DATE_ARRIVAL:
                                    binding.edtArrivedDate.setText(displayDate);
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, year, month, day);

            DatePicker datePicker = datePickerDialog.getDatePicker();
            datePicker.setMinDate(today.getTimeInMillis());
            datePickerDialog.show();
        } catch (Exception e) {
            CommonUtils.printExceptionTrace(TAG, e);
        }
    }

    private void updateLabel(int type){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        if (type == DATE_DEPARTURE){
            binding.edtDepartureDate.setText(dateFormat.format(myCalendar.getTime()));
        }
        else{
            binding.edtArrivedDate.setText(dateFormat.format(myCalendar.getTime()));
        }
    }

    private void onEditClicked(){

        TicketModel ticket = new TicketModel(binding.edtTicketId.getText().toString(), binding.edtCarPlate.getText().toString(), binding.edtToLocation.getText().toString(),binding.edtFromLocation.getText().toString(),binding.editDepartureTime.getText().toString(), binding.edtDepartureDate.getText().toString(),binding.etArrivedTime.getText().toString(), binding.edtArrivedDate.getText().toString(), binding.edtCompanyName.getText().toString(), binding.edtTicketPrice.getText().toString(), binding.edtTicketStage.getText().toString(),uid);
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference("ticket");
        mDatabase.child(uid).child(binding.edtTicketId.getText().toString()).setValue(ticket).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity_user_edit_ticket.this)
                        .setTitle("Data Updated")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        }).show();
            }
        });
    }
}