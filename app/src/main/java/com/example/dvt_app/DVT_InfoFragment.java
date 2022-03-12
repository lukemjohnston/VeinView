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


public class DVT_InfoFragment extends Fragment {

    Button dvt_info_next_button_1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_d_v_t__info, container, false);

        dvt_info_next_button_1 = view.findViewById(R.id.dvt_info_next_button_1);


        dvt_info_next_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_DVT_InfoFragment_to_DVT_InfoFragment_2);
            }
        });

        return view;
    }
}