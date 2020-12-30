package com.alaminkarno.firebasegmailpasswordauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.PointerIcon;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText nameET,emailET,passwordET,confPasswordET;
    Button sign_upBTN;
    ProgressBar progressBar;
    String name,email,password,confPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        init();

        sign_upBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString();
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                confPassword = confPasswordET.getText().toString();

                if(name.isEmpty()){
                    nameET.setError("Enter you name");
                }
                else if(email.isEmpty()){
                    emailET.setError("Enter email address");
                }
                else if(password.isEmpty() || password.length() <6){
                    passwordET.setError("Enter 6 digit password");
                }
                else if(confPassword.isEmpty() || confPassword.length() < 6){
                    confPasswordET.setError("Confirm your password");
                }
                else if(!password.equals(confPassword)){
                    confPasswordET.setError("Confirm Password does not match");
                }
                else {

                    signupUser();
                }
            }
        });
    }

    private void signupUser() {

        progressBar.setVisibility(View.VISIBLE);
        sign_upBTN.setVisibility(View.GONE);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                sign_upBTN.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confPasswordET = findViewById(R.id.confirmPasswordET);
        sign_upBTN = findViewById(R.id.sign_upBTN);
        progressBar = findViewById(R.id.loadingBar);

        firebaseAuth = FirebaseAuth.getInstance();
    }
}