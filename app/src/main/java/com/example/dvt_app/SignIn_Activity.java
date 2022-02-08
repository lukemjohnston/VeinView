package com.example.dvt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;



public class SignIn_Activity extends AppCompatActivity {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button button = (Button)findViewById(R.id.SignIn);
        TextView signUp = (TextView) findViewById(R.id.TextViewSignUp);
        TextView email = (TextView) findViewById(R.id.EditTextEmail);
        TextView password = (TextView) findViewById(R.id.EditTextPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }



}