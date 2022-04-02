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
    List<String> riskText;
    List<Integer> riskIMG;
    Context context;
    List<String> desc;

    public MyAdaptor(Context c, List<String> dsc, List<String> d, List<String> t, List<Integer> rIMG, List<String> rText) {
        context = c;
        desc = dsc;
        date = d;
        test = t;
        riskIMG = rIMG;
        riskText = rText;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_record, parent, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int p = position;
        holder.tvDate.setText(date.get(p));
        holder.tvTest.setText(test.get(p));
        holder.ivRisk.setImageResource(riskIMG.get(p));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("desc", desc.get(p));
                bundle.putString("test", test.get(p));
                bundle.putString("risk", riskText.get(p));
                bundle.putString("date", date.get(p));

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
