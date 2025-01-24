package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class medicine_view_holder extends RecyclerView.ViewHolder {

    TextView medicineTitle;
    TextView medicineelement;
    TextView medicineused;

    public medicine_view_holder(@NonNull View itemView) {
        super(itemView);
        medicineTitle = itemView.findViewById(R.id.medicineTitle);
        medicineelement = itemView.findViewById(R.id.medicineelement);
        medicineused = itemView.findViewById(R.id.medicineused);
    }
}
