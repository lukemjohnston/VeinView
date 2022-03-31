package com.example.dvt_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    TextView name, email, phone, birthday;
    Button submit;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        phone = view.findViewById(R.id.profilePhone);
        birthday = view.findViewById(R.id.profileBirthday);
        submit = view.findViewById(R.id.button_profile_submit);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userId = user.getUid();

        email.setText(user.getEmail());

        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(Objects.requireNonNull(snapshot.child("Name").getValue()).toString());
                phone.setText(Objects.requireNonNull(snapshot.child("Phone").getValue()).toString());
                birthday.setText(Objects.requireNonNull(snapshot.child("Birthdate").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newName = name.getText().toString();
                String newPhone = phone.getText().toString();
                String newBirthday = birthday.getText().toString();
                //String newEmail = email.getText().toString();


                if (newName.length() > 50){
                    FancyToast.makeText(getContext(), "Name can not be larger than 50 characters",
                            FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
                else if (newPhone.length() > 20) {
                    FancyToast.makeText(getContext(), "Phone number can not be larger than 20 characters",
                            FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
                else if (newBirthday.length() > 15) {
                    FancyToast.makeText(getContext(), "Birthdate can not be larger than 15 characters",
                            FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
                else {
                    //user.updateEmail(newEmail);
                    mDatabase.child("users").child(userId).child("Name").setValue(newName);
                    mDatabase.child("users").child(userId).child("Phone").setValue(newPhone);
                    mDatabase.child("users").child(userId).child("Birthdate").setValue(newBirthday);

                    FancyToast.makeText(getContext(), "Your account has been updated",
                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }

            }
        });




        return view;
    }
}