package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class medicinie_adapter extends RecyclerView.Adapter<medicine_view_holder> {


    Context context;
    List<medicine_item> items;

    public medicinie_adapter(Context context, List<medicine_item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public medicine_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new medicine_view_holder(LayoutInflater.from(context).inflate(R.layout.medicine_item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull medicine_view_holder holder, int position) {
        holder.medicineTitle.setText(items.get(position).getName());
        holder.medicineelement.setText(items.get(position).getElement());
        holder.medicineused.setText(items.get(position).getCuring());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
