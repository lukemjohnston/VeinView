package com.example.dvt_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class HomeFragment extends Fragment {

    Button startWells, startPPG;
    TextView DVTInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);

        startWells = view.findViewById(R.id.buttonWells);
        startPPG = view.findViewById(R.id.buttonPPG);
        DVTInfo = view.findViewById(R.id.dvtInfo);

        startWells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_navigation_home_to_wells_Screen2);
            }
        });

        startPPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_navigation_home_to_PPG_Test2);
            }
        });

        DVTInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_navigation_home_to_DVT_InfoFragment);
            }
        });

        return view;
    }







}