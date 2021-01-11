package com.alaminkarno.firebasegmailpasswordauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button verifyBTN;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        checkVerification();

        verifyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Verification Link Sent Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void checkVerification() {

        if(!firebaseUser.isEmailVerified()){

            textView.setVisibility(View.VISIBLE);
            verifyBTN.setVisibility(View.VISIBLE);
        }
        else {
            textView.setText("Email Verified");
            verifyBTN.setVisibility(View.INVISIBLE);
        }
    }

    private void init() {
        textView = findViewById(R.id.verifyTV);
        verifyBTN = findViewById(R.id.verifyBTN);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkVerification();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkVerification();
    }
}