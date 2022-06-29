package com.nihon.przusoslite;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity
{
    private TextView[] days = new TextView[7];
    private TextView currentDate;
    private Button changeDateButton;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        days[0] = findViewById(R.id.day_1);
        days[1] = findViewById(R.id.day_2);
        days[2] = findViewById(R.id.day_3);
        days[3] = findViewById(R.id.day_4);
        days[4] = findViewById(R.id.day_5);
        days[5] = findViewById(R.id.day_6);
        days[6] = findViewById(R.id.day_7);

        calendar = Calendar.getInstance();

        currentDate = findViewById(R.id.currentDateText);
        changeDateButton = findViewById(R.id.changeDateButton);

        changeDateButton.setOnClickListener(v -> initDatePicker());

        setDays(calendar);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) ->
        {
            calendar.set(year, month, day);
            setDays(calendar);
        };

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private void setDays(Calendar calendar)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String text = sdf.format(calendar.getTime());

        currentDate.setText(text);

        for (int i = 0; i < 7; i++)
        {
            days[i].setText(new SimpleDateFormat("d").format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

}
