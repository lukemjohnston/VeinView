package com.example.dvt_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RecordsFragment extends Fragment {

    TextView HowToRead;

    //String date[], testType[];
    List<String> desc = new ArrayList<String>(10);
    List<String> date = new ArrayList<String>(10);
    List<String> testType = new ArrayList<String>(10);
    List<String> risk = new ArrayList<String>(10);
    List<Integer> riskImg = new ArrayList<Integer>(10);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();
    DatabaseReference mRef;
    MyAdaptor myAdaptor;
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;


    public class SampleHolder extends RecyclerView.ViewHolder {
        public SampleHolder(View itemView) {
            super(itemView);
        }
    }

    // SampleRecycler.java
    public class SampleRecycler extends RecyclerView.Adapter<SampleHolder> {
        @Override
        public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(SampleHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_records, container, false);

        getData(view);

        HowToRead = view.findViewById(R.id.how_to_read);

        HowToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_navigation_records_to_how_To_Read_Fragment);
            }
        });



        return view;
    }

    public void getData(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new SampleRecycler());



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userId = user.getUid();

        mRef = mDatabase.child("tests").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    desc.clear();
                    date.clear();
                    testType.clear();
                    riskImg.clear();

                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String d = ds.child("Date").getValue().toString();
                        date.add(d);

                        String test = ds.child("Type").getValue().toString();
                        testType.add(test);

                        String r = ds.child("Risk").getValue().toString();
                        risk.add(r);

                        String descrpt;
                        if (test.equals("PPG")) {
                            descrpt = "Left Leg: " + (Objects.requireNonNull(ds.child("LeftLeg").getValue()).toString())
                                    + "\n\nRight Leg: " + (Objects.requireNonNull(ds.child("RightLeg").getValue()).toString());
                        } else {
                            descrpt = "Q1: " + (Objects.requireNonNull(ds.child("Question1").getValue()).toString())
                                    + "\n\nQ2: " + (Objects.requireNonNull(ds.child("Question2").getValue()).toString())
                                    + "\n\nQ3: " + (Objects.requireNonNull(ds.child("Question3").getValue()).toString())
                                    + "\n\nQ4: " + (Objects.requireNonNull(ds.child("Question4").getValue()).toString())
                                    + "\n\nQ5: " + (Objects.requireNonNull(ds.child("Question5").getValue()).toString())
                                    + "\n\nQ6: " + (Objects.requireNonNull(ds.child("Question6").getValue()).toString())
                                    + "\n\nQ7: " + (Objects.requireNonNull(ds.child("Question7").getValue()).toString())
                                    + "\n\nQ8: " + (Objects.requireNonNull(ds.child("Question8").getValue()).toString())
                                    + "\n\nQ9: " + (Objects.requireNonNull(ds.child("Question9").getValue()).toString())
                                    + "\n\nQ10: " + (Objects.requireNonNull(ds.child("Question10").getValue()).toString());
                        }
                        desc.add(descrpt);
                    }
                }

                for (int i = 0; i < risk.size(); i++)
                {
                    if (risk.get(i).equals("Low")) {
                        riskImg.add(R.drawable.green_dot);
                    }
                    if (risk.get(i).equals("Moderate")) {
                        riskImg.add(R.drawable.yellow_dot);
                    }
                    if (risk.get(i).equals("High")) {
                        riskImg.add(R.drawable.red_dot);
                    }
                }



                myAdaptor = new MyAdaptor(getContext(), desc, date, testType, riskImg, risk);
                recyclerView.setAdapter(myAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}