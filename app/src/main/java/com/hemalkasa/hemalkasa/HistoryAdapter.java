package com.hemalkasa.hemalkasa;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private static final String TAG = "pratik";
    private List<Prescription_Table> prescription = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "HistoryAdapter" + getItemCount());
        Prescription_Table prescriptionTable=prescription.get(position);
        Log.d(TAG, prescriptionTable.getVisiting_date());
        holder.VisitingDate.setText(prescriptionTable.getVisiting_date());
        holder.PogWeeks.setText(prescriptionTable.getPog_weeks());
        holder.PogDays.setText(prescriptionTable.getPog_days());
        holder.EDDDays.setText(prescriptionTable.getEdd());
    }

    @Override
    public int getItemCount() {
        return prescription.size();
    }

    public void setHistoryList(List<Prescription_Table> prescription){
        this.prescription=prescription;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView VisitingDate,PogWeeks,PogDays,EDDDays;

        public ViewHolder(View itemView) {
            super(itemView);
            VisitingDate=itemView.findViewById(R.id.VisitingDate);
            PogWeeks=itemView.findViewById(R.id.PogWeeks);
            PogDays=itemView.findViewById(R.id.PogDays);
            EDDDays=itemView.findViewById(R.id.EDDDays);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Prescription_Table prescriptionTable=prescription.get(position);
                        listener.getHistory(prescriptionTable);
                    }
                }
            });
        }
    }


    public interface OnItemClickListener{
        void getHistory(Prescription_Table prescriptionTable);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;   //Initializing listener of interface
    }

}
