package com.example.dvt_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.auth.User;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();


    @IgnoreExtraProperties
    public class User {

        public String Name;
        public String Phone;
        public String Birthdate;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String name, String phone, String birthdate) {      //fills user variable with the user data
            this.Name = name;
            this.Phone = phone;
            this.Birthdate = birthdate;
        }
    }


    public void writeNewUser(String name, String email, String phone, String birthdate, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp_Activity.this, new OnCompleteListener<AuthResult>() {     //creates a authenticated user account
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {      // Sign up success
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser userAuth = mAuth.getCurrentUser();     //set user to the newly created user

                            User user = new User(name, phone, birthdate);
                            assert userAuth != null;
                            mDatabase.child("users").child(userAuth.getUid()).setValue(user)   //store other user data, with the key being the auth user ID
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mAuth.getCurrentUser().sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        FancyToast.makeText(getApplicationContext(),
                                                                "Account created:\nPlease verify your email address",
                                                                FancyToast.LENGTH_LONG, FancyToast.DEFAULT, false).show();

                                                        Intent intent = new Intent(SignUp_Activity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                    }
                                                    else {
                                                        Log.w(TAG, "sendEmailVerification:failure", task.getException());
                                                        FancyToast.makeText(getApplicationContext(), task.getException().getMessage(),
                                                                FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                                    }
                                                }
                                            });

                                        }

                                    });

                        } else {    //sign up fails
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
    }


    public void createUser() {
        String Name = ((TextView) findViewById(R.id.editTextName)).getText().toString();
        String Email = ((TextView) findViewById(R.id.editTextEmail)).getText().toString();
        String Phone = ((TextView) findViewById(R.id.editTextPhone)).getText().toString();
        String Birthdate = ((TextView) findViewById(R.id.editTextDate)).getText().toString();
        String Password = ((TextView) findViewById(R.id.editTextPassword)).getText().toString();

        boolean emailCheck = android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches();

        if (!emailCheck) {
            FancyToast.makeText(getApplicationContext(), "Please enter a valid email address",
                    FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        }
        else if (Password.length() <= 6) {
            FancyToast.makeText(getApplicationContext(), "Password must be at least 6 characters",
                    FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
        }
        if (Name.length() > 50){
            FancyToast.makeText(getApplicationContext(), "Name can not be larger than 50 characters",
                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
        else if (Phone.length() > 20) {
            FancyToast.makeText(getApplicationContext(), "Phone number can not be larger than 20 characters",
                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
        else if (Birthdate.length() > 15) {
            FancyToast.makeText(getApplicationContext(), "Birthdate can not be larger than 15 characters",
                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
        else if ((Name.length() == 0) || (Phone.length() == 0) || (Birthdate.length() == 0)) {
            FancyToast.makeText(getApplicationContext(), "All text boxes must have a value",
                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
        else {
            writeNewUser(Name, Email, Phone, Birthdate, Password);


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(SignUp_Activity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Button submit = (Button)findViewById(R.id.SignUpButton);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

}