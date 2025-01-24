package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class app_view_controller2 extends RecyclerView.ViewHolder{
    TextView Name;
    TextView Phone,id;
    Button deleteButton;
    public app_view_controller2(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        Name = itemView.findViewById(R.id.textViewName);
        Phone = itemView.findViewById(R.id.textViewNumber);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}
