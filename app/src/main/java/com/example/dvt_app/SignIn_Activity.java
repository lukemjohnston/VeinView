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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;



public class SignIn_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;



    void ResetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = ((TextView) findViewById(R.id.EditTextEmail)).getText().toString();

        if (email.length() == 0) {
            FancyToast.makeText(SignIn_Activity.this, "Please put your account email in the text box, so we can send you a password reset email",
                    FancyToast.LENGTH_LONG, FancyToast.WARNING,false).show();
        }
        else {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                FancyToast.makeText(SignIn_Activity.this, "Password reset email has been sent",
                                        FancyToast.LENGTH_LONG, FancyToast.DEFAULT,false).show();
                            }
                            else {
                                FancyToast.makeText(SignIn_Activity.this, task.getException().getMessage(),
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                            }

                        }
                    });
        }


    }

    void UserSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Intent intent = new Intent(SignIn_Activity.this, HomeActivity.class);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                            else {
                                FirebaseAuth.getInstance().signOut();

                                FancyToast.makeText(SignIn_Activity.this, "Please verify your email address",
                                        FancyToast.LENGTH_LONG, FancyToast.WARNING,false).show();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            FancyToast.makeText(SignIn_Activity.this, task.getException().getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        Button signIn = (Button)findViewById(R.id.SignIn);
        TextView newPassword = (TextView)findViewById(R.id.ForgotPass);
        TextView signUp = (TextView) findViewById(R.id.TextViewSignUp);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {     //user is already signed in
            //FirebaseAuth.getInstance().signOut();
            //FancyToast.makeText(getApplicationContext(), "Welcome", FancyToast.LENGTH_LONG,FancyToast.DEFAULT,false).show();
            Intent intent = new Intent(SignIn_Activity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((TextView) findViewById(R.id.EditTextEmail)).getText().toString();
                String password = ((TextView) findViewById(R.id.EditTextPassword)).getText().toString();
                UserSignIn(email, password);
            }
        });

        newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword();
            }
        });
    }


}