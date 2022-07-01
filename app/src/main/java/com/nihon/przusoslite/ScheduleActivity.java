package com.nihon.przusoslite;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScheduleActivity extends AppCompatActivity
{
    private OAuth10aService service;
    private OAuth1AccessToken accessToken;

    private JSONArray JSONScheduleDataArray;
    private List<JSONObject> listOfActivities;

    private final int daysCount = 7;
    private TextView[] days = new TextView[daysCount];
    private TextView[] daysSymbol = new TextView[daysCount];
    private int activeDay = 0;
    private TextView currentDate;
    private Button changeDateButton;
    private RecyclerView recyclerView;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private String[] daysOfWeek = new String[daysCount];
    private String currentDateStr;

    private ArrayList<ScheduleActivityModel> scheduleActivityModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        service = OAuthServiceSingleton.getInstance().getService();
        accessToken = OAuthServiceSingleton.getInstance().getAccessToken();

        recyclerView = findViewById(R.id.scheduleRecycler);

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

        LoadScheduleData loadScheduleData = new LoadScheduleData();
        loadScheduleData.execute(currentDateStr);
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

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        currentDateStr = sdf2.format(calendar.getTime());

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

        calendar.add(Calendar.DAY_OF_MONTH, -daysCount);

        LoadScheduleData loadScheduleData = new LoadScheduleData();
        loadScheduleData.execute(currentDateStr);
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

        setDays(calendar);
    }

    class LoadScheduleData extends AsyncTask<String, Void, Void>
    {
        Response response = null;

        @Override
        protected Void doInBackground(String... strings)
        {
            String requestStr = "https://usosapps.prz.edu.pl/services/tt/user?start=" + strings[0] + "&days=1&fields=start_time|end_time|room_number|name";
            final OAuthRequest request = new OAuthRequest(Verb.GET, requestStr);
            service.signRequest(accessToken, request);

            try
            {
                response = service.execute(request);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            super.onPostExecute(unused);
            scheduleActivityModels.clear();

            try
            {
                JSONScheduleDataArray = new JSONArray(response.getBody());

                listOfActivities = new ArrayList<JSONObject>(JSONScheduleDataArray.length());

                for (int i = 0; i < JSONScheduleDataArray.length(); i++)
                {
                    listOfActivities.add(JSONScheduleDataArray.getJSONObject(i));
                }

                for (int i = 0; i < listOfActivities.size(); i++)
                {
                    String startTime = listOfActivities.get(i).getString("start_time");
                    String endTime = listOfActivities.get(i).getString("end_time");
                    String room = listOfActivities.get(i).getString("room_number");

                    JSONObject obj = listOfActivities.get(i).getJSONObject("name");
                    String activity = obj.getString("pl");

                    if (startTime.length() >= 19)
                    {
                        startTime = startTime.substring(startTime.length()-8);
                    }

                    if (endTime.length() >= 19)
                    {
                        endTime = endTime.substring(endTime.length()-8);
                    }

                    Time start = new Time(Integer.parseInt(startTime.substring(0, 2)), Integer.parseInt(startTime.substring(3, 5)),Integer.parseInt(startTime.substring(6)));
                    Time end = new Time(Integer.parseInt(endTime.substring(0, 2)), Integer.parseInt(endTime.substring(3, 5)),Integer.parseInt(endTime.substring(6)));

                    addScheduleActivity(start, end, activity, "prof", room);
                }
            } catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }

            ScheduleRecycleViewAdapter adapter = new ScheduleRecycleViewAdapter(ScheduleActivity.this, scheduleActivityModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this));
        }
    }

}
