package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView tvLogin;
    private TextInputEditText tieName,tieEmail,tiePassword,tieConfirmPassword;
    private Button buttonSignup;
    String emailPattern = Patterns.EMAIL_ADDRESS.pattern();
    String modifiedEmail;
    ProgressDialog pd;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference userref,docref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findAllViews();

        setClickListener();

    }

    private void setClickListener(){
        tvLogin.setOnClickListener((View v)-> finish());

        buttonSignup.setOnClickListener((View v)->{
            performAuth();
        });
    }

    private void performAuth() {
        String name = String.valueOf( tieName.getText() );
        String email = String.valueOf( tieEmail.getText() );
        String pass = String.valueOf( tiePassword.getText() );
        String confirmPass = String.valueOf( tieConfirmPassword.getText() );

        if (name.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(SignUpActivity.this, "Enter correct Email", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (pass.length()<6) {
            Toast.makeText(SignUpActivity.this, "Password has to be of at least length 6", Toast.LENGTH_SHORT).show();
        } else if (confirmPass.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Confirm Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!confirmPass.equals(pass)) {
            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else{
            pd.setMessage("Please wait...");
            pd.setTitle("Registration");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            modifiedEmail = email.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            modifiedEmail = modifiedEmail.substring(0, modifiedEmail.length() - 3);
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            patient p = new patient(modifiedEmail, name, email, "...", "...", "No", "...", "...", "...", 0, "");
                            userref.child(modifiedEmail).setValue(p);
                            pd.dismiss();
                            Intent intent = new Intent(SignUpActivity.this, ConfirmActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        pd.dismiss();
                        Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void findAllViews(){
        tieName = findViewById(R.id.tieName);
        tieEmail = findViewById(R.id.tieEmail);
        tiePassword = findViewById(R.id.tiePassword);
        tieConfirmPassword = findViewById(R.id.tieConfirmPassword);
        buttonSignup = findViewById(R.id.buttonRegister);
        tvLogin = findViewById(R.id.tvLogin);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userref = FirebaseDatabase.getInstance().getReference().child("patient");

    }




}
