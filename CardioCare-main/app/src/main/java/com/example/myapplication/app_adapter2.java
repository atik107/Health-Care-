package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class app_adapter2 extends RecyclerView.Adapter<app_view_controller2>{
    Context context;
    List<app_item2> items;

    public void setSearchList(List<app_item2> dataSearchList){
        this.items = dataSearchList;
        notifyDataSetChanged();
    }

    public app_adapter2(Context context, List<app_item2> items) {
        this.context = context;
        this.items = items;
    }

    interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

    private app_adapter.OnDeleteItemClickListener deleteItemClickListener;

    public void setOnDeleteItemClickListener(app_adapter.OnDeleteItemClickListener listener) {
        this.deleteItemClickListener = listener;
    }

    @NonNull
    @Override
    public app_view_controller2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new app_view_controller2(LayoutInflater.from(context).inflate(R.layout.item_appointment2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull app_view_controller2 holder, int position) {
        holder.Name.setText(items.get(position).getPname());
        holder.Phone.setText(items.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
