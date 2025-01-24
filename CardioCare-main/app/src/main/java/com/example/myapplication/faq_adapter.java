package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class faq_adapter extends RecyclerView.Adapter<faq_view_holder>{

    Context context;
    List<faq_item> items;

    public void setSearchList(List<faq_item> dataSearchList){
        this.items = dataSearchList;
        notifyDataSetChanged();
    }
    public faq_adapter(Context context, List<faq_item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public faq_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new faq_view_holder(LayoutInflater.from(context).inflate(R.layout.faqlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull faq_view_holder holder, int position) {
        holder.Question.setText(items.get(position).getQues());
        holder.Answer.setText(items.get(position).getAns());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
