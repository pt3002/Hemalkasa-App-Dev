package com.example.prescriptionrecyclerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class NewAdapter extends FirebaseRecyclerAdapter<PrescriptionModel, NewAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param mainActivity
     */
    MainActivity activity;
    public NewAdapter(@NonNull FirebaseRecyclerOptions<PrescriptionModel> options, MainActivity mainActivity) {
        super(options);
        this.activity = mainActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PrescriptionModel model) {
        holder.day.setText(model.day);
        holder.dose.setText(model.dose);
        holder.med.setText(model.medicine);
        holder.time.setText(model.time);
        int p = position;
        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPrescription editPrescription = com.example.prescriptionrecyclerapp.editPrescription.newInstance((String) holder.med.getText(), (String) holder.dose.getText(), (String) holder.day.getText(), (String) holder.time.getText(), getRef(p).getKey(), activity, p);
                editPrescription.show(activity.getSupportFragmentManager(), "test");
            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("entries").child(Objects.requireNonNull(getRef(p).getKey())).removeValue();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView med, dose, day, time;
        FloatingActionButton editbtn, deletebtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            med = itemView.findViewById(R.id.medicine);
            dose = itemView.findViewById(R.id.dose);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            editbtn = itemView.findViewById(R.id.edit);
            deletebtn = itemView.findViewById(R.id.delete);
        }
    }
}
