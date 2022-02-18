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

public class SignUp_Activity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();


    @IgnoreExtraProperties
    public static class User {

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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {     //creates a authenticated user account
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
                                            Toast.makeText(getApplicationContext(), "ACCOUNT CREATED", Toast.LENGTH_LONG).show();
                                        }
                                    });

                        } else {    //sign up fails
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "ACCOUNT CREATION FAILED",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button submit = (Button)findViewById(R.id.SignUpButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = findViewById(R.id.editTextName).toString();
                String Email = findViewById(R.id.editTextEmail).toString();
                String Phone = findViewById(R.id.editTextPhone).toString();
                String Birthdate = findViewById(R.id.editTextDate).toString();
                String Password = findViewById(R.id.editTextPassword).toString();

                writeNewUser(Name, Email, Phone, Birthdate, Password);

                //Intent intent = new Intent(SignUp_Activity.this, SignIn_Activity.class);
                //startActivity(intent);
            }
        });
    }

}