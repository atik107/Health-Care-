package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class amb_adapter extends RecyclerView.Adapter<amb_view_holder>{
    Context context;
    List<amb_items> items;

    public void setSearchList(List<amb_items> dataSearchList){
        this.items = dataSearchList;
        notifyDataSetChanged();
    }
    public amb_adapter(Context context, List<amb_items> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public amb_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new amb_view_holder(LayoutInflater.from(context).inflate(R.layout.item_ambulance,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull amb_view_holder holder, int position) {
        holder.Name.setText(items.get(position).getName());
        holder.Phone.setText(items.get(position).getPhone());
        String phone2 = items.get(position).getPhone();
        int p = position;
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the phone number is available
                if (phone2 != null && !phone2.isEmpty()) {
                    // Create an intent to make a phone call
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone2));
                    v.getContext().startActivity(intent);
                } else {
                    // Handle the case where the phone number is not available
                    Toast.makeText(v.getContext(), "Phone number not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
