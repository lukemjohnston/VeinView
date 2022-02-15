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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.auth.User;

public class SignUp_Activity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = database.getReference();

    @IgnoreExtraProperties
    public class User {

        public String Name;
        public String Email;
        public String Phone;
        public String Birthdate;
        public String Password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String name, String email, String phone, String birthdate, String password) {
            this.Name = name;
            this.Email = email;
            this.Phone = phone;
            this.Birthdate = birthdate;
            this.Password = password;
        }

    }




    public void writeNewUser(String username, String name, String email, String phone, String birthdate, String password) {
        User user = new User(name, email, phone, birthdate, password);
        mDatabase.child("users").child(username).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast toast = Toast.makeText(getApplicationContext(), "ACCOUNT CREATED", Toast.LENGTH_LONG);
                toast.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button submit = (Button)findViewById(R.id.SignUpButton);
        TextView usernameTV = (TextView) findViewById(R.id.editTextUsername);
        TextView nameTV = (TextView) findViewById(R.id.editTextName);
        TextView emailTV = (TextView) findViewById(R.id.editTextEmail);
        TextView phoneTV = (TextView) findViewById(R.id.editTextPhone);
        TextView birthdateTV = (TextView) findViewById(R.id.editTextDate);
        TextView passwordTV = (TextView) findViewById(R.id.editTextPassword);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = usernameTV.getText().toString();
                String Name = nameTV.getText().toString();
                String Email = emailTV.getText().toString();
                String Phone = phoneTV.getText().toString();
                String Birthdate = birthdateTV.getText().toString();
                String Password = passwordTV.getText().toString();

                writeNewUser(Username, Name, Email, Phone, Birthdate, Password);

                Intent intent = new Intent(SignUp_Activity.this, SignIn_Activity.class);
                startActivity(intent);

            }
        });
    }


}