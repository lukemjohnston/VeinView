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

    public void SubmitPPG(View v, String left, String right, String risk) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DateFormat df = new SimpleDateFormat("yyyy_MM_dd 'at' HH_mm_ss z");
        String dateId = df.format(Calendar.getInstance().getTime());
        DateFormat df2 = new SimpleDateFormat("M/d/yyyy");
        String date = df2.format(Calendar.getInstance().getTime());

        PPG_Test.PPG ppg = new PPG_Test.PPG(left, right, risk, date);

        assert user != null;
        mDatabase.child("tests").child(user.getUid()).child(dateId).setValue(ppg);

        FancyToast.makeText(getContext(), "TEST ADDED TO RECORDS",
                FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

        Navigation.findNavController(v).navigate(R.id.action_PPG_Test_to_navigation_home);
    }


    public void riskEval(float numL, float numR, View v, String left, String right) {
        String risk;

        if ((numL >= 100) || (numR >= 100)) {
            FancyToast.makeText(getContext(), "INVALID PPG NUMBER: Value too high",
                    FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }
        else if ((numL < 0) || (numR < 0)) {
            FancyToast.makeText(getContext(), "INVALID PPG NUMBER: Value must be positive",
                    FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }
        else if ((numL >= 21) && (numR >= 21)) {
            if ((numL >= 50) || (numR >= 50)) {
                FancyToast.makeText(getContext(), "HIGH PGG: Please double check monitor reading",
                        FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
            }
            risk = "Low";
            SubmitPPG(v, left, right, risk);
        }
        else if ((numL <= 10) || (numR <= 10)) {
            risk = "High";
            SubmitPPG(v, left, right, risk);
        }
        else {
            risk = "Moderate";
            SubmitPPG(v, left, right, risk);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_p_p_g__test, container, false);

        Button submit = view.findViewById(R.id.ppg_submit);
        TextView leftLeg = view.findViewById(R.id.ppg_left);
        TextView rightLeg = view.findViewById(R.id.ppg_right);


<<<<<<< Updated upstream
        /*PPG Test Values Range
        * Low (Green) Risk: 21 - 50
        * Medium (Yellow) Risk: 11 - 20
        * High (Red) Risk: 0 - 10
        * Warning: Unusually High Value: 51 - 100
        * Invalid Value: <0 or >100
        * Unaccepted Values: Decimal values and String objects (Error handling yet to be implemented)
        * */

=======
>>>>>>> Stashed changes
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float numL, numR;

                String left = leftLeg.getText().toString();
                String right = rightLeg.getText().toString();

                try {
                    numL = Float.parseFloat(left);
                    numR = Float.parseFloat(right);
                    riskEval(numL, numR, v, left, right);
                }
                catch (Exception e) {
                    FancyToast.makeText(getContext(), "INVALID PPG NUMBER",
                            FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }

            }
        });

        return view;
    }


}