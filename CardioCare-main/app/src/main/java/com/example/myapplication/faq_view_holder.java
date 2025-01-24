package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class faq_view_holder extends RecyclerView.ViewHolder{
    TextView Question;
    TextView Answer;
    public faq_view_holder(@NonNull View itemView) {
        super(itemView);
        Question = itemView.findViewById(R.id.Question);
        Answer = itemView.findViewById(R.id.Answer);
    }
}
