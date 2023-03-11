package com.example.prescriptionrecyclerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    MainActivity mainActivity;
    ArrayList<PrescriptionModel> arr;
    public  RecyclerViewAdapter(Context context, ArrayList<PrescriptionModel> arr){
        this.context = context;
        this.arr = arr;
        this.mainActivity = (MainActivity) context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.each_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.med.setText(arr.get(position).medicine);
        holder.dose.setText(arr.get(position).dose);
        holder.day.setText(arr.get(position).day);
        holder.time.setText(arr.get(position).time);
        int p = position;
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPrescription editPrescription = com.example.prescriptionrecyclerapp.editPrescription.newInstance((String) holder.med.getText(), (String) holder.dose.getText(), (String) holder.day.getText(), (String) holder.time.getText(), "string", mainActivity, p);
                editPrescription.show(mainActivity.getSupportFragmentManager(), "test");

            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.delArrData(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView med, dose, day, time;
        FloatingActionButton editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            med = itemView.findViewById(R.id.medicine);
            dose = itemView.findViewById(R.id.dose);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            editBtn = itemView.findViewById(R.id.edit);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
    }
}
