package com.nihon.przusoslite;

public class GradesChildModel
{
    private String activityName;
    private float grade;

    public GradesChildModel(String activityName, float grade)
    {
        this.activityName = activityName;
        this.grade = grade;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public float getGrade()
    {
        return grade;
    }
}