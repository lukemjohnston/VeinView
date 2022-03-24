package com.example.dvt_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dvt_app.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_records, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /*Button startWells = (Button)findViewById(R.id.buttonWells);
        Button startPPG = (Button)findViewById(R.id.buttonPPG);

        //Button wells = (Button)findViewById(R.id.wells_submit);


        startWells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_wells_Screen2);
            }
        });

        startPPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wellsToDB();
            }
        });

        wells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wellsToDB();
            }
        });*/
    }





}