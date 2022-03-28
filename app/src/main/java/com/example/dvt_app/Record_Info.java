package com.example.dvt_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Record_Info extends Fragment {

    TextView riskTV, testTV, dateTV, descTV;
    String dataSnap, risk, test, date, desc;

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
        risk = (getArguments().getString("dataSnap"));



    }

    private void setData() {
        riskTV.setText(risk);
        testTV.setText(test);
        dateTV.setText(date);
    }

    }