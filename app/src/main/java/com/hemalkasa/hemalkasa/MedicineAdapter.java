package com.hemalkasa.hemalkasa;

import android.content.Context;
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

    // No need for separate Model Class
    //Here Medicine_Table(Entitiy) can work the same way as the modal class used to work
    //Since it also has the required struct and getter/setter methods implemented
    private List<Medicine_Table> medicineList=new ArrayList<>();
    Add_Medicines add_medicines;

    //If we pass array from the constructor then the medicineList will get initialized only once
    public MedicineAdapter(){
//        this.add_medicines = add_medicines;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from((Context) add_medicines).inflate(R.layout.each_row, parent, false);
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_row, parent, false);
//        Log.d(TAG, "onCreateViewHolder: ");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d(TAG, String.valueOf(medicineList.size()));
        Medicine_Table medicineTable=medicineList.get(position);
        holder.med.setText(medicineTable.getName());
        holder.dose.setText(medicineTable.getDose());
        holder.day.setText(medicineTable.getDate());
        holder.time.setText(medicineTable.getTime());
        holder.frequency.setText(medicineTable.getFrequency());
//        Log.d(TAG, "onBindViewHolder: "+ medicineTable.getName());

//        int p = position;
//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                add_medicines.delMedicineArrData(p);
//            }
//        });
//        holder.editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditMedicine editMedicine = EditMedicine.newInstance(holder.med.getText().toString(), holder.dose.getText().toString(), holder.day.getText().toString(), holder.time.getText().toString(), holder.frequency.getText().toString(), add_medicines, p);
//                editMedicine.show(add_medicines.getSupportFragmentManager(), "test");
//            }
//        });
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView med, dose, day, time, frequency;
        FloatingActionButton editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            med = itemView.findViewById(R.id.medicine);
            dose = itemView.findViewById(R.id.dose);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            frequency = itemView.findViewById(R.id.frequency);
            editBtn = itemView.findViewById(R.id.edit);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
    }
}
