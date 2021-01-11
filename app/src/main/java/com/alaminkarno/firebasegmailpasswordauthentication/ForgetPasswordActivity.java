package com.alaminkarno.firebasegmailpasswordauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText emailET;
    Button resetBTN;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        init();

        resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString().trim();

                if(email.isEmpty()){
                    emailET.setError("Enter your email for reset password");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    resetBTN.setVisibility(View.INVISIBLE);

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            resetBTN.setVisibility(View.VISIBLE);

                            emailET.setText("");

                            Toast.makeText(ForgetPasswordActivity.this, "Reset Mail Send Successfully. Check your Mail.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);
                            resetBTN.setVisibility(View.VISIBLE);
                            Toast.makeText(ForgetPasswordActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void init() {
        emailET = findViewById(R.id.emailET);
        resetBTN = findViewById(R.id.resetBTN);
        progressBar = findViewById(R.id.loadingBar);
    }
}