package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class app_adapter extends RecyclerView.Adapter<app_view_controller> {
    Context context;
    List<app_item> items;

    public void setSearchList(List<app_item> dataSearchList){
        this.items = dataSearchList;
        notifyDataSetChanged();
    }

    public app_adapter(Context context, List<app_item> items) {
        this.context = context;
        this.items = items;
    }

    interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

    private OnDeleteItemClickListener deleteItemClickListener;

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        this.deleteItemClickListener = listener;
    }

    @NonNull
    @Override
    public app_view_controller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new app_view_controller(LayoutInflater.from(context).inflate(R.layout.item_appointment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull app_view_controller holder, int position) {
        holder.Name.setText(items.get(position).getName());
        holder.Phone.setText(items.get(position).getDate());
        int p = position;
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteItemClickListener != null) {
                    deleteItemClickListener.onDeleteItemClick(p);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
