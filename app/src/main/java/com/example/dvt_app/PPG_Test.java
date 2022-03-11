package com.example.dvt_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PPG_Test extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();


    @IgnoreExtraProperties
    public class PPG {
        public String Type;
        public String Risk;
        public String Date;
        public String UpperLeft;
        public String UpperRight;
        public String MiddleLeft;
        public String MiddleRight;
        public String LowerLeft;
        public String LowerRight;

        public PPG() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public PPG(String upperLeft, String upperRight, String middleLeft, String middleRight,
                   String lowerLeft, String lowerRight, String risk, String date) {
            this.Type = "PPG";
            this.Risk = risk;                              //fills user variable with the user data
            this.Date = date;                              //fills user variable with the user data
            this.UpperLeft = upperLeft;
            this.UpperRight = upperRight;
            this.MiddleLeft = middleLeft;
            this.MiddleRight = middleRight;
            this.LowerLeft = lowerLeft;
            this.LowerRight = lowerRight;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_p_p_g__test, container, false);

        Button submit = view.findViewById(R.id.ppg_submit);
        TextView ul = view.findViewById(R.id.ppg_upper_left);
        TextView ur = view.findViewById(R.id.ppg_upper_right);
        TextView ml = view.findViewById(R.id.ppg_middle_left);
        TextView mr = view.findViewById(R.id.ppg_middle_right);
        TextView ll = view.findViewById(R.id.ppg_lower_left);
        TextView lr = view.findViewById(R.id.ppg_lower_right);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy_MM_dd 'at' HH_mm_ss z");
                String dateId = df.format(Calendar.getInstance().getTime());
                DateFormat df2 = new SimpleDateFormat("M/d/yyyy");
                String date = df2.format(Calendar.getInstance().getTime());

                String risk = "Low";

                String upperLeft = ul.getText().toString();
                String upperRight = ur.getText().toString();
                String middleLeft = ml.getText().toString();
                String middleRight = mr.getText().toString();
                String lowerLeft = ll.getText().toString();
                String lowerRight = lr.getText().toString();


                PPG_Test.PPG ppg = new PPG_Test.PPG(upperLeft, upperRight, middleLeft, middleRight,
                        lowerLeft, lowerRight, risk, date);

                assert user != null;
                mDatabase.child("tests").child(user.getUid())
                        .child(dateId).setValue(ppg);

                Navigation.findNavController(v)
                        .navigate(R.id.action_PPG_Test_to_navigation_home);
            }
        });
        return view;
    }
}