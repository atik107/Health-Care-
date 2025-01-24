package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class amb_view_holder extends RecyclerView.ViewHolder{

    TextView Name;
    TextView Phone,id;
    ImageView callButton;
    public amb_view_holder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        Name = itemView.findViewById(R.id.textViewName);
        Phone = itemView.findViewById(R.id.textViewNumber);
        callButton = itemView.findViewById(R.id.callButton);
    }
}
