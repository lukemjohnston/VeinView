package com.example.dvt_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    List<String> date;
    List<String> test;
    List<Integer> risk;
    Context context;

    public MyAdaptor(Context ct, List<String> d, List<String> t, List<Integer> r) {
        context = ct;
        date = d;
        test = t;
        risk = r;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (context);
        View view = inflater.inflate(R.layout.my_record, parent, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDate.setText(date.get(position));
        holder.tvTest.setText(test.get(position));
        holder.ivRisk.setImageResource(risk.get(position));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate, tvTest;
        ImageView ivRisk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvTest = itemView.findViewById(R.id.test);
            ivRisk = itemView.findViewById(R.id.risk);

        }

    }
}
