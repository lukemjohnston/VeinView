package com.example.dvt_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class RecordsFragment extends Fragment {

    //String date[], testType[];
    List<String> dataSnaps = new ArrayList<String>(10);
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
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String dataSnap = ds.getKey();
                        dataSnaps.add(dataSnap);

                        String d = ds.child("Date").getValue().toString();
                        date.add(d);

                        String test = ds.child("Type").getValue().toString();
                        testType.add(test);

                        String r = ds.child("Risk").getValue().toString();
                        risk.add(r);

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
                    //Toast.makeText(getActivity(), String.valueOf(risk.size()), Toast.LENGTH_SHORT).show();
                }

                //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                //mLayoutManager.setReverseLayout(true);
                //mLayoutManager.setStackFromEnd(true);
                myAdaptor = new MyAdaptor(getContext(), dataSnaps, date, testType, riskImg);
                recyclerView.setAdapter(myAdaptor);
                //recyclerView.setLayoutManager(mLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }
}