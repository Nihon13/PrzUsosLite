package com.nihon.przusoslite;

import java.util.List;

public class GradesParentModel
{
    private String semester;
    private List<GradesChildModel> gradesChildModelList;

    public GradesParentModel(String semester, List<GradesChildModel> gradeChildModelList)
    {
        this.semester = semester;
        this.gradesChildModelList = gradeChildModelList;
    }

    public String getSemester()
    {
        return semester;
    }

    public List<GradesChildModel> getGradeChildModelList()
    {
        return gradesChildModelList;
    }
}