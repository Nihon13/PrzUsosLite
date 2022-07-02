package com.nihon.przusoslite;

public class CoursesGradesModel
{
    private int semester;
    private String courseName;
    private String grade;

    public CoursesGradesModel(int semester, String courseName, String grade)
    {
        this.semester = semester;
        this.courseName = courseName;
        this.grade = grade;
    }

    public int getSemester()
    {
        return semester;
    }

    public void setSemester(int semester)
    {
        this.semester = semester;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }
}