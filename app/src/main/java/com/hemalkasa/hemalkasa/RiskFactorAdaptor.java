package com.hemalkasa.hemalkasa;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RiskFactorAdaptor extends RecyclerView.Adapter<RiskFactorAdaptor.ViewHolder> {
    private static final String TAG = "pratik";
    private List<Risk_Factor_Table> riskFactorTableList = new ArrayList<>();
    private OnItemClickListener listener;
    private Boolean isAccess;

    public RiskFactorAdaptor(Boolean isAccess) {
        this.isAccess = isAccess;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.risk_factor_item, parent, false);
        return new ViewHolder(itemview);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Risk_Factor_Table riskFactorTable = riskFactorTableList.get(position);
        holder.RiskName.setText((riskFactorTable.getName()));
        if (riskFactorTable.getRisk()) {
            holder.RiskName.setCheckMarkDrawable(R.drawable.checked);
            holder.RiskName.setBackgroundColor(R.color.white);
            // TODO Change this checked icon
            holder.RiskName.setChecked(true);
        } else {
            holder.RiskName.setBackground(null);
            holder.RiskName.setCheckMarkDrawable(null);
            holder.RiskName.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return riskFactorTableList.size();
    }

    public void setRiskFactorTableList(List<Risk_Factor_Table> riskFactorTables) {
        this.riskFactorTableList = riskFactorTables;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView RiskName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RiskName = itemView.findViewById(R.id.RiskName);

            if(isAccess) {
                RiskName.setClickable(false);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            Risk_Factor_Table riskFactorTable = riskFactorTableList.get(position);
                            listener.toggleRisk(riskFactorTable);
                        }
                    }
                });
            }
            else{
                RiskName.setClickable(true);
            }
        }
    }

    public interface OnItemClickListener {
        void toggleRisk(Risk_Factor_Table riskFactorTable);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
