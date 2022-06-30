package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GradesActivity extends AppCompatActivity
{
    private ArrayList<GradesParentModel> gradesParentModels = new ArrayList<>();
    private ArrayList<GradesChildModel> gradesChildModels = new ArrayList<>();
    private ArrayList<GradesChildModel> gradesChildModels2 = new ArrayList<>();
    private ArrayList<GradesChildModel> gradesChildModels3 = new ArrayList<>();

    private List<List<GradesChildModel>> gradesChildModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        RecyclerView recyclerView = findViewById(R.id.parentGradesRecycler);

        addGradeChildren(gradesChildModels, "Angielski", 5.0f);
        addGradeChildren(gradesChildModels, "Polski", 3.5f);
        addGradeChildren(gradesChildModels, "Matematyka", 5.0f);
        addGradeChildren(gradesChildModels, "Historia", 2.0f);
        addGradeChildren(gradesChildModels, "Religia", 4.5f);
        addGradeChildren(gradesChildModels, "Sieci", 3.0f);

        addGradeChildren(gradesChildModels2, "Sieci", 3.0f);
        addGradeChildren(gradesChildModels2, "Sieci2", 3.5f);
        addGradeChildren(gradesChildModels2, "Polski2", 4.5f);
        addGradeChildren(gradesChildModels2, "C++", 5.0f);

        addGradeChildren(gradesChildModels3, "Java", 5.0f);
        addGradeChildren(gradesChildModels3, "Web", 5.0f);
        addGradeChildren(gradesChildModels3, "Eliak", 4.5f);
        addGradeChildren(gradesChildModels3, "Bazy", 4.0f);
        addGradeChildren(gradesChildModels3, "Fizyka", 2.0f);
        addGradeChildren(gradesChildModels3, "Bazy2", 3.5f);

        gradesChildModelsList.add(gradesChildModels);
        gradesChildModelsList.add(gradesChildModels2);
        gradesChildModelsList.add(gradesChildModels3);

        addGradeParent();

        GradesRecycleViewParentAdapter adapter = new GradesRecycleViewParentAdapter(gradesParentModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addGradeChildren(List<GradesChildModel> list, String activity, float grade)
    {
        list.add(new GradesChildModel(activity, grade));
    }

    private void addGradeParent()
    {
        String semester = "";
        int number = 0;

        for (int i = 0; i < gradesChildModelsList.size(); i++)
        {
            number = i+1;
            semester = getResources().getString(R.string.semester) + " " + number + ".";
            gradesParentModels.add(new GradesParentModel(semester, gradesChildModelsList.get(i)));
        }
    }
}