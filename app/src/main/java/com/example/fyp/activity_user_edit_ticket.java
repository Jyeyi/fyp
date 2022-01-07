package com.example.fyp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.fyp.databinding.ActivityAddTicketBinding;
import com.example.fyp.databinding.ActivityEditTicketBinding;
import com.example.fyp.databinding.ToolbarViewBinding;
import com.example.fyp.utility.CommonUtils;
import com.example.fyp.utility.CustomTextUtils;
import com.example.fyp.utility.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

import static android.content.ContentValues.TAG;

public class activity_user_edit_ticket extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;
    private ToolbarViewBinding toolbar;
    private ActivityEditTicketBinding binding;
    EditText etBusId, etBusPlateNo, etToLocation, etFromLocation, etDepartureDate, etDepartureTime, etArriveTime, etArrivedDate;
    EditText etTicketPrice, etStage, etCompanyName;
    final Calendar myCalendar= Calendar.getInstance();
    String ticketId, busPlateNo, toLocation, fromLocation, departureDate, departureTime, arriveTime, arriveDate, ticketPrice, stage, companyName;
    // date picker
    private static final int DATE_DEPARTURE = 1;
    private static final int DATE_ARRIVAL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTicketBinding.inflate(getLayoutInflater());
        toolbar = binding.includedToolbar;
        View view = binding.getRoot();
        setContentView(view);

        setToolbar();
        setOnClick();
        initDetails();
    }

    private void initDetails(){

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
}