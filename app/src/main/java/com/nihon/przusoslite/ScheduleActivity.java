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
    private final int daysCount = 7;
    private TextView[] days = new TextView[daysCount];
    private TextView[] daysSymbol = new TextView[daysCount];
    private int activeDay = 0;
    private TextView currentDate;
    private Button changeDateButton;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private String[] daysOfWeek = new String[daysCount];

    private ArrayList<ScheduleActivityModel> scheduleActivityModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        RecyclerView recyclerView = findViewById(R.id.scheduleRecycler);

        daysOfWeek[0] = getString(R.string.sundaysym);
        daysOfWeek[1] = getString(R.string.mondaysym);
        daysOfWeek[2] = getString(R.string.tuesdaysym);
        daysOfWeek[3] = getString(R.string.wednesdaysym);
        daysOfWeek[4] = getString(R.string.thursdaysym);
        daysOfWeek[5] = getString(R.string.fridaysym);
        daysOfWeek[6] = getString(R.string.saturdaysym);

        days[0] = findViewById(R.id.day_1);
        days[1] = findViewById(R.id.day_2);
        days[2] = findViewById(R.id.day_3);
        days[3] = findViewById(R.id.day_4);
        days[4] = findViewById(R.id.day_5);
        days[5] = findViewById(R.id.day_6);
        days[6] = findViewById(R.id.day_7);

        daysSymbol[0] = findViewById(R.id.day_1_symbol);
        daysSymbol[1] = findViewById(R.id.day_2_symbol);
        daysSymbol[2] = findViewById(R.id.day_3_symbol);
        daysSymbol[3] = findViewById(R.id.day_4_symbol);
        daysSymbol[4] = findViewById(R.id.day_5_symbol);
        daysSymbol[5] = findViewById(R.id.day_6_symbol);
        daysSymbol[6] = findViewById(R.id.day_7_symbol);

        for (int i = 0; i < daysCount; i++)
        {
            int finalI = i;
            days[i].setOnClickListener(v -> activateDay(finalI));
        }

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

        calendar = Calendar.getInstance();

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

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < daysCount; i++)
        {
            days[i].setText(new SimpleDateFormat("d").format(calendar.getTime()));
            if (dayOfWeek+i >= daysCount)
            {
                daysSymbol[i].setText(daysOfWeek[dayOfWeek+i-daysCount]);
            }
            else
            {
                daysSymbol[i].setText(daysOfWeek[dayOfWeek+i]);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void addScheduleActivity(Time startTime, Time endTime, String activityName, String prof, String room)
    {
        scheduleActivityModels.add(new ScheduleActivityModel(startTime, endTime, activityName, prof, room));
    }

    private void activateDay(int index)
    {
        if (activeDay != index)
        {
            days[activeDay].setBackground(null);
            activeDay = index;
            days[activeDay].setBackground(getResources().getDrawable(R.drawable.activeborder));
        }
    }

}
