package com.nihon.przusoslite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleRecycleViewAdapter extends RecyclerView.Adapter<ScheduleRecycleViewAdapter.MyViewHolder>
{
    Context context;
    ArrayList<ScheduleActivityModel> scheduleActivityModels;

    public ScheduleRecycleViewAdapter(Context context, ArrayList<ScheduleActivityModel> scheduleActivityModels)
    {
        this.context = context;
        this.scheduleActivityModels = scheduleActivityModels;
    }

    @NonNull
    @Override
    public ScheduleRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_calendarrow, parent, false);

        return new ScheduleRecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleRecycleViewAdapter.MyViewHolder holder, int position)
    {
        holder.tvStartHour.setText(scheduleActivityModels.get(position).getStartTime());
        holder.tvEndHour.setText(scheduleActivityModels.get(position).getEndTime());
        holder.tvActivityName.setText(scheduleActivityModels.get(position).getActivityTitle());
        holder.tvProf.setText(scheduleActivityModels.get(position).getProfessor());
        holder.tvRoom.setText(scheduleActivityModels.get(position).getRoom());
    }

    @Override
    public int getItemCount()
    {
        return scheduleActivityModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvStartHour;
        TextView tvEndHour;
        TextView tvActivityName;
        TextView tvProf;
        TextView tvRoom;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvStartHour = itemView.findViewById(R.id.startHour);
            tvEndHour = itemView.findViewById(R.id.endHour);
            tvActivityName = itemView.findViewById(R.id.activityName);
            tvProf = itemView.findViewById(R.id.professor);
            tvRoom = itemView.findViewById(R.id.room);
        }
    }
}