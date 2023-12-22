package com.example.caronrentrenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Date_Book extends AppCompatActivity {

    EditText dp1, dp2;
    Calendar calendar;
    Button btn;
    Calendar calendar2;
    int count, s, e;
    String d1, d2;
    TextView day, rent, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_book);

        dp1 = findViewById(R.id.dp);
        btn = findViewById(R.id.button);
        day = findViewById(R.id.day);
        rent = findViewById(R.id.rent);
        total = findViewById(R.id.totalRent);
        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                s = dayOfMonth;

                updateCalendar();
            }

            private void updateCalendar() {
                String format = "dd/MM/yyyy"; // Corrected date format pattern
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

                dp1.setText(sdf.format(calendar.getTime()));
            }
        };

        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Date_Book.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        dp2 = findViewById(R.id.dp2);
        calendar2 = Calendar.getInstance(); // Remove the redundant declaration

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year2, int month2, int dayOfMonth2) {
                calendar2.set(Calendar.YEAR, year2);
                calendar2.set(Calendar.MONTH, month2);
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);

                e = dayOfMonth2;

                updateCalendar2();

                count = e - s;

                d1 = dp1.getText().toString();
                d2 = dp2.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    java.util.Date datee1 = simpleDateFormat.parse(d1);
                    java.util.Date datee2 = simpleDateFormat.parse(d2);

                    // Check if selected day is the same as dp1's day
                    if (datee2.getDate() == calendar.get(Calendar.DAY_OF_MONTH)) {
                        Toast.makeText(Date_Book.this, "Selected day is not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    long different = (Math.abs(datee2.getTime()) - datee1.getTime());

                    long diffday = (different / (24 * 60 * 60 * 1000));
                    Log.i("Test", "Days" + diffday);
                    String diff = String.valueOf(diffday);

                    day.setText("DAYS : " + diff);
                    rent.setText("RENT : " + "1000");
                    long tot = diffday * 1000;

                    String ans = String.valueOf(tot);

                    total.setText("TOTAL : " + ans);

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            private void updateCalendar2() {
                String format = "dd/MM/yyyy"; // Corrected date format pattern
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                dp2.setText(sdf.format(calendar2.getTime()));
            }
        };

        dp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(Date_Book.this, date2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                // Set the minimum date for datePickerDialog2 as selected date from datePickerDialog plus one day
                datePickerDialog2.getDatePicker().setMinDate(calendar.getTimeInMillis() + (24 * 60 * 60 * 1000));

                datePickerDialog2.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic for handling the booking action
            }
        });
    }
}
