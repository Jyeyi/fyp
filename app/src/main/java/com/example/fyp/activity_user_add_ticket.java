package com.example.fyp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.utility.CommonUtils;
import com.example.fyp.utility.CustomTextUtils;
import com.example.fyp.utility.DateUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

import static android.content.ContentValues.TAG;

public class activity_user_add_ticket extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private ToolbarViewBinding toolbar;
    private ActivityAddTicketBinding binding;
    EditText etBusId, etBusPlateNo, etToLocation, etFromLocation, etDepartureDate, etDepartureTime, etArriveTime, etArrivedDate;
    EditText etTicketPrice, etStage, etCompanyName;
    final Calendar myCalendar= Calendar.getInstance();
    // date picker
    private static final int DATE_DEPARTURE = 1;
    private static final int DATE_ARRIVAL = 2;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference dbref;
    SharedPreferences prefs;
    String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTicketBinding.inflate(getLayoutInflater());
        toolbar = binding.includedToolbar;
        View view = binding.getRoot();
        setContentView(view);

        setToolbar();
        setOnClick();

        prefs = PreferenceManager.getDefaultSharedPreferences(activity_user_add_ticket.this);
        uid = prefs.getString("Uid", "defaultStringIfNothingFound");

        auth = FirebaseAuth.getInstance();
    }

    private void setToolbar() {
        toolbar.tvTitle.setText("Add Ticket");
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
                finish();
            }
        });

        binding.llDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.print("test departure date");
                Log.d("1","test departure date");

                new DatePickerDialog(activity_user_add_ticket.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                showDatePicker(DATE_DEPARTURE, binding.edtDepartureDate.getText().toString());
            }
        });

        binding.llArrivedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity_user_add_ticket.this,date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               storeData();
            }
        });

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

    private void storeData(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ticketID", binding.edtTicketId.getText().toString());
        hashMap.put("busPlateNumber", binding.edtCarPlate.getText().toString());
        hashMap.put("toLocation", binding.edtToLocation.getText().toString());
        hashMap.put("fromLocation", binding.edtFromLocation.getText().toString());
        hashMap.put("departureTime", binding.editDepartureTime.getText().toString());
        hashMap.put("departureDate", binding.edtDepartureDate.getText().toString());
        hashMap.put("arrivedTime", binding.etArrivedTime.getText().toString());
        hashMap.put("arrivedDate", binding.edtArrivedDate.getText().toString());
        hashMap.put("companyName", binding.edtCompanyName.getText().toString());
        hashMap.put("ticketPrice", binding.edtTicketPrice.getText().toString());
        hashMap.put("stage",binding.edtTicketStage.getText().toString());


        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("ticket");
        dbref.child("qwerf557555g66gg77uh6").child(binding.edtTicketId.getText().toString()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity_user_add_ticket.this, "Success Add", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
//                intent.putExtra(INTENT_VIEWTYPE, viewType);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_user_add_ticket.this, "Fail Add", Toast.LENGTH_LONG).show();
            }
        });
    }
}

//    EditText etBusId, etBusPlateNo, etToLocation, etFromLocation, etDepartureDate, etDepartureTime, etArriveTime, etArrivedDate;
//    EditText etTicketPrice, etStage, etCompanyName;