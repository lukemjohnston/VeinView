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
                                            FancyToast.makeText(getApplicationContext(), "ACCOUNT CREATED",
                                                    FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                        }
                                    });

                        } else {    //sign up fails
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            FancyToast.makeText(getApplicationContext(), "ACCOUNT CREATION FAILED",
                                    FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
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
                String Name = ((TextView) findViewById(R.id.editTextName)).getText().toString();
                String Email = ((TextView) findViewById(R.id.editTextEmail)).getText().toString();
                String Phone = ((TextView) findViewById(R.id.editTextPhone)).getText().toString();
                String Birthdate = ((TextView) findViewById(R.id.editTextDate)).getText().toString();
                String Password = ((TextView) findViewById(R.id.editTextPassword)).getText().toString();

                writeNewUser(Name, Email, Phone, Birthdate, Password);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignUp_Activity.this, HomeActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

}