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

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private static final String TAG = "pratik";

    private List<Medicine_Table> medicineList = new ArrayList<>();
    private OnItemClickListener listener;

    // No need for separate Model Class
    //Here Medicine_Table(Entitiy) can work the same way as the modal class used to work
    //Since it also has the required struct and getter/setter methods implemented

    //If we pass array from the constructor then the medicineList will get initialized only once
    public MedicineAdapter(){
//        this.add_medicines = add_medicines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine_Table medicineTable=medicineList.get(position);
        Log.d(TAG, String.valueOf(medicineTable.getId()) + "  " + medicineTable.getName());
        holder.med.setText(medicineTable.getName());
        holder.dose.setText(medicineTable.getDose());
        holder.day.setText(medicineTable.getDate());
        holder.time.setText(medicineTable.getTime());
        holder.frequency.setText(medicineTable.getFrequency());
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    //Since we call this functon in observe of Live Data
    //Everytime the list gets modified this medicineList will also be reinitialized again
    public void setMedicines(List<Medicine_Table> medicineList){
//        Log.d(TAG, "Inside Adapter :" + String.valueOf(medicineList.size()));
        this.medicineList=medicineList;
    }

    //Returns the Medical_Table at the particular position
    public Medicine_Table getMedicalTableAt(int position){
        return medicineList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView med, dose, day, time, frequency;
        FloatingActionButton editBtn, deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            med = itemView.findViewById(R.id.medicine);
            dose = itemView.findViewById(R.id.dose);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            frequency = itemView.findViewById(R.id.frequency);
            editBtn = itemView.findViewById(R.id.edit);
            deleteBtn = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(medicineList.get(position));
                    }
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(medicineList.get(position));
                    }
                }
            });

        }
    }

    //Interface classs implemented here itself
    public interface OnItemClickListener {
        //Work of iterface is to just define methods
        void onItemClick(Medicine_Table medicineTable);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;   //Initializing listener of interface
    }
}