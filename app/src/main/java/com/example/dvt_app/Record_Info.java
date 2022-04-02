package com.example.dvt_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class Record_Info extends Fragment {

    TextView riskTV, testTV, dateTV, descTV;
    String risk, test, date, desc;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();
    DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_record__info, container, false);

        riskTV = view.findViewById(R.id.riskLevel);
        testTV = view.findViewById(R.id.testType);
        dateTV = view.findViewById(R.id.dateInfo);
        descTV = view.findViewById(R.id.moreInfo);

        getData();
        setData();

        return view;
    }

    private void getData() {
        assert getArguments() != null;
        desc = (getArguments().getString("desc"));
        risk = ("Risk Level: "+(getArguments().getString("risk")));
        test = ("Test Type: "+(getArguments().getString("test")));
        date = (getArguments().getString("date"));
    }

    private void setData() {
        riskTV.setText(risk);
        testTV.setText(test);
        dateTV.setText(date);
        descTV.setText(desc);
    }

    }