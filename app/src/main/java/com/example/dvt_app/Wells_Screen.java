package com.example.dvt_app;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Wells_Screen extends Fragment {
    
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();


    @IgnoreExtraProperties
    public class Wells {
        public String Type;
        public String Risk;
        public String Date;
        public Boolean Question1;
        public Boolean Question2;
        public Boolean Question3;
        public Boolean Question4;
        public Boolean Question5;
        public Boolean Question6;
        public Boolean Question7;
        public Boolean Question8;
        public Boolean Question9;
        public Boolean Question10;


        public Wells() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Wells(Boolean q1, Boolean q2, Boolean q3, Boolean q4, Boolean q5,
                     Boolean q6, Boolean q7, Boolean q8, Boolean q9,
                     Boolean q10, String risk, String date) {
            this.Type = "Wells";
            this.Risk = risk;                              //fills user variable with the user data
            this.Date = date;                              //fills user variable with the user data
            this.Question1 = q1;
            this.Question2 = q2;
            this.Question3 = q3;
            this.Question4 = q4;
            this.Question5 = q5;
            this.Question6 = q6;
            this.Question7 = q7;
            this.Question8 = q8;
            this.Question9 = q9;
            this.Question10 = q10;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_wells__screen, container, false);

        Button submit = view.findViewById(R.id.wells_submit);
        CheckBox q1 = view.findViewById(R.id.question1);
        CheckBox q2 = view.findViewById(R.id.question2);
        CheckBox q3 = view.findViewById(R.id.question3);
        CheckBox q4 = view.findViewById(R.id.question4);
        CheckBox q5 = view.findViewById(R.id.question5);
        CheckBox q6 = view.findViewById(R.id.question6);
        CheckBox q7 = view.findViewById(R.id.question7);
        CheckBox q8 = view.findViewById(R.id.question8);
        CheckBox q9 = view.findViewById(R.id.question9);
        CheckBox q10 = view.findViewById(R.id.question10);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy_MM_dd 'at' HH_mm_ss z");
                String dateId = df.format(Calendar.getInstance().getTime());
                DateFormat df2 = new SimpleDateFormat("M/d/yyyy");
                String date = df2.format(Calendar.getInstance().getTime());

                int riskNum = 0;
                String risk = null;

                if (q1.isChecked())
                    riskNum += 1;
                if (q2.isChecked())
                    riskNum += 1;
                if (q3.isChecked())
                    riskNum += 1;
                if (q4.isChecked())
                    riskNum += 1;
                if (q5.isChecked())
                    riskNum += 1;
                if (q6.isChecked())
                    riskNum += 1;
                if (q7.isChecked())
                    riskNum += 1;
                if (q8.isChecked())
                    riskNum += 1;
                if (q9.isChecked())
                    riskNum += 1;
                if (q10.isChecked())
                    riskNum += 1;

                if (riskNum == 0)
                    risk = "Low";
                else if ((riskNum == 1) || (riskNum == 2))
                    risk = "Moderate";
                else if (riskNum >= 3)
                    risk = "High";


                Wells_Screen.Wells wells = new Wells_Screen.Wells(q1.isChecked(), q2.isChecked(),
                        q3.isChecked(), q4.isChecked(), q5.isChecked(), q6.isChecked(),
                        q7.isChecked(), q8.isChecked(), q9.isChecked(), q10.isChecked(), risk, date);

                assert user != null;



                mDatabase.child("tests").child(user.getUid())
                        .child(dateId).setValue(wells);

                FancyToast.makeText(getContext(), "TEST ADDED TO RECORDS",
                        FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                Navigation.findNavController(v)
                        .navigate(R.id.action_wells_Screen_to_navigation_home);
            }
        });
        return view;
    }
}