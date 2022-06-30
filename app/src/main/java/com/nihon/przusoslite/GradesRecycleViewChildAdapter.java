package com.nihon.przusoslite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradesRecycleViewChildAdapter extends RecyclerView.Adapter<GradesRecycleViewChildAdapter.MyViewHolder>
{
    private List<GradesChildModel> gradesChildModelList;

    GradesRecycleViewChildAdapter(List<GradesChildModel> gradesChildModelList)
    {
        this.gradesChildModelList = gradesChildModelList;
    }

    @NonNull
    @Override
    public GradesRecycleViewChildAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_grades_child_item, parent, false);
        return new GradesRecycleViewChildAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradesRecycleViewChildAdapter.MyViewHolder holder, int position)
    {
        holder.tvActivityName.setText(gradesChildModelList.get(position).getActivityName());
        String val = String.valueOf(gradesChildModelList.get(position).getGrade());
        holder.tvGrade.setText(val);
    }

    @Override
    public int getItemCount()
    {
        return gradesChildModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvActivityName;
        TextView tvGrade;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvActivityName = itemView.findViewById(R.id.activityName);
            tvGrade = itemView.findViewById(R.id.gradeText);
        }
    }
}