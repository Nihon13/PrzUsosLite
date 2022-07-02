package com.nihon.przusoslite;

public class GradesChildModel
{
    private String activityName;
    private String grade;

    public GradesChildModel(String activityName, String grade)
    {
        this.activityName = activityName;
        this.grade = grade;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public String getGrade()
    {
        return grade;
    }
}