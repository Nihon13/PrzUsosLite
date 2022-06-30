package com.nihon.przusoslite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradesRecycleViewParentAdapter extends RecyclerView.Adapter<GradesRecycleViewParentAdapter.MyViewHolder>
{
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<GradesParentModel> gradesParentModelList;

    GradesRecycleViewParentAdapter(List<GradesParentModel> gradesParentModelList)
    {
        this.gradesParentModelList = gradesParentModelList;
    }

    @NonNull
    @Override
    public GradesRecycleViewParentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_grades_parent_item, parent, false);
        return new GradesRecycleViewParentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradesRecycleViewParentAdapter.MyViewHolder holder, int position)
    {
        holder.tvSemester.setText(gradesParentModelList.get(position).getSemester());
        GradesParentModel childModel = gradesParentModelList.get(position);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyViewHolder.getChildRecyclerView().getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager.setInitialPrefetchItemCount(childModel.getGradeChildModelList().size());

        GradesRecycleViewChildAdapter childAdapter = new GradesRecycleViewChildAdapter(childModel.getGradeChildModelList());
        MyViewHolder.childRecyclerView.setLayoutManager(layoutManager);
        MyViewHolder.childRecyclerView.setAdapter(childAdapter);
        MyViewHolder.childRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount()
    {
        return gradesParentModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private static RecyclerView childRecyclerView;
        private TextView tvSemester;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvSemester = itemView.findViewById(R.id.semesterText);
            childRecyclerView = itemView.findViewById(R.id.childGradesRecycler);
        }

        public static RecyclerView getChildRecyclerView()
        {
            return childRecyclerView;
        }
    }
}