package com.nihon.przusoslite;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity
{
    private TextView[] days = new TextView[7];
    private TextView currentDate;
    private Button changeDateButton;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private ArrayList<ScheduleActivityModel> scheduleActivityModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        RecyclerView recyclerView = findViewById(R.id.calendarRecycler);

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

        addScheduleActivity(new Time(10,15,00), new Time(11, 45, 00), "Przedmiot 1", "dr Imie Nazwisko", "B107");
        addScheduleActivity(new Time(12,25,00), new Time(13, 50, 00), "Przedmiot 2", "dr Imie Nazwisko", "D210");
        addScheduleActivity(new Time(15,00,00), new Time(16, 00, 00), "Przedmiot 3", "dr Imie Nazwisko", "A03");
        addScheduleActivity(new Time(16,30,00), new Time(17, 00, 00), "Przedmiot 4", "dr Imie Nazwisko", "A023");
        addScheduleActivity(new Time(17,30,00), new Time(18, 00, 00), "Przedmiot 5", "dr Imie Nazwisko", "C133");
        addScheduleActivity(new Time(18,30,00), new Time(19, 00, 00), "Przedmiot 6", "dr Imie Nazwisko", "A13");
        addScheduleActivity(new Time(19,00,00), new Time(22, 00, 00), "Przedmiot 7", "dr Imie Nazwisko", "D01");

        ScheduleRecycleViewAdapter adapter = new ScheduleRecycleViewAdapter(this, scheduleActivityModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void addScheduleActivity(Time startTime, Time endTime, String activityName, String prof, String room)
    {
        scheduleActivityModels.add(new ScheduleActivityModel(startTime, endTime, activityName, prof, room));
    }

}
