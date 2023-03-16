package com.hemalkasa.hemalkasa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    ArrayList<MedicineModel> medicineArr;
    Add_Medicines add_medicines;
    public MedicineAdapter(Add_Medicines add_medicines, ArrayList<MedicineModel> medicineArr){
        this.add_medicines = add_medicines;
        this.medicineArr = medicineArr;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((Context) add_medicines).inflate(R.layout.each_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.med.setText(medicineArr.get(position).medicine);
        holder.dose.setText(medicineArr.get(position).dose);
        holder.day.setText(medicineArr.get(position).day);
        holder.time.setText(medicineArr.get(position).time);
        holder.frequency.setText(medicineArr.get(position).frequency);
        int p = position;
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_medicines.delMedicineArrData(p);
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditMedicine editMedicine = EditMedicine.newInstance(holder.med.getText().toString(), holder.dose.getText().toString(), holder.day.getText().toString(), holder.time.getText().toString(), holder.frequency.getText().toString(), add_medicines, p);
                editMedicine.show(add_medicines.getSupportFragmentManager(), "test");
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicineArr.size();
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
