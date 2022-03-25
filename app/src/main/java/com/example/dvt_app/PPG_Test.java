package com.example.dvt_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.shashank.sony.fancytoastlib.FancyToast;

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
        public String LeftLeg;
        public String RightLeg;

        public PPG() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public PPG(String left, String right, String risk, String date) {
            this.Type = "PPG";
            this.Risk = risk;
            this.Date = date;
            this.LeftLeg = left;
            this.RightLeg = right;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_p_p_g__test, container, false);

        Button submit = view.findViewById(R.id.ppg_submit);
        TextView leftLeg = view.findViewById(R.id.ppg_left);
        TextView rightLeg = view.findViewById(R.id.ppg_right);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("yyyy_MM_dd 'at' HH_mm_ss z");
                String dateId = df.format(Calendar.getInstance().getTime());
                DateFormat df2 = new SimpleDateFormat("M/d/yyyy");
                String date = df2.format(Calendar.getInstance().getTime());

                String risk = "Low";

                String left = leftLeg.getText().toString();
                String right = rightLeg.getText().toString();

                PPG_Test.PPG ppg = new PPG_Test.PPG(left, right, risk, date);

                assert user != null;
                mDatabase.child("tests").child(user.getUid())
                        .child(dateId).setValue(ppg);

                FancyToast.makeText(getContext(), "TEST ADDED TO RECORDS",
                        FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                Navigation.findNavController(v)
                        .navigate(R.id.action_PPG_Test_to_navigation_home);
            }
        });
        return view;
    }
}