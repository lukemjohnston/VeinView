package com.example.dvt_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    List<String> date;
    List<String> test;
    List<Integer> risk;
    Context context;
    List<String> dataSnap;

    public MyAdaptor(Context ct, List<String> ds, List<String> d, List<String> t, List<Integer> r) {
        context = ct;
        dataSnap = ds;
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

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyToast.makeText(context, "Welcome", FancyToast.LENGTH_LONG,FancyToast.DEFAULT,false).show();

                Bundle bundle = new Bundle();
                bundle.putString("dataSnap", dataSnap.get(position));

                Navigation.findNavController(view).navigate(R.id.action_navigation_records_to_record_Info, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate, tvTest;
        ImageView ivRisk;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvTest = itemView.findViewById(R.id.test);
            ivRisk = itemView.findViewById(R.id.risk);
            mainLayout = itemView.findViewById(R.id.myRecord);

        }

    }
}
