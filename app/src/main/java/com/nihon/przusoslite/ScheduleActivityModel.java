package com.nihon.przusoslite;

import java.sql.Time;

public class ScheduleActivityModel
{
    private Time startTime;
    private Time endTime;
    private String activityTitle;
    private String professor;
    private String room;


    public ScheduleActivityModel(Time startTime, Time endTime, String activityTitle, String professor, String room)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityTitle = activityTitle;
        this.professor = professor;
        this.room = room;
    }

    public String getStartTime()
    {
        String text = startTime.getHours() + ":";

        int minutes = startTime.getMinutes();
        if (minutes < 10)
        {
            text = text + "0" + minutes;
        }
        else
        {
            text = text + minutes;
        }

        return text;
    }

    public String getEndTime()
    {
        String text = endTime.getHours() + ":";

        int minutes = endTime.getMinutes();
        if (minutes < 10)
        {
            text = text + "0" + minutes;
        }
        else
        {
            text = text + minutes;
        }

        return text;
    }

    public String getActivityTitle()
    {
        return activityTitle;
    }

    public String getProfessor()
    {
        return professor;
    }

    public String getRoom()
    {
        return room;
    }
}

