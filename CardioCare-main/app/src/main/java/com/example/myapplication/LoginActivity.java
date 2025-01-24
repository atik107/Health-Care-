package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tieEmail,tiePassword;
    private TextView tvSignUp,tvForgot;
    private Button buttonLogin;
    DatabaseReference databaseReference;
    Switch Switch;

    String emailPattern = Patterns.EMAIL_ADDRESS.pattern();
    ProgressDialog pd;
    String modifiedEmail;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    int x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Switch = findViewById(R.id.switch2);
        findAllViews();

        setClickListener();

        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Switch.setText("Doctor");
                    x=1;
                }
                else{
                    Switch.setText("Patient");
                    x=0;
                }
            }
        });
    }


    private void setClickListener(){
        tvSignUp.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,SignUpActivity.class)));
        tvForgot.setOnClickListener(v -> {
            if (x == 1) {
                Toast.makeText(LoginActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
            }
            else {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });

        buttonLogin.setOnClickListener((View v)->{
            if(x==0){
                userlog();
//                Intent intent = new Intent(LoginActivity.this, PatientActivity.class);
//                startActivity(intent);
//                finish();
            }
            else{
                doctorlog();
//                Intent intent = new Intent(LoginActivity.this, MenuActivity2.class);
//                startActivity(intent);
//                finish();
            }

        });

    }

    private void doctorlog() {
        String email = String.valueOf(tieEmail.getText());
        String pass = String.valueOf(tiePassword.getText());

        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(LoginActivity.this, "Enter correct Email", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            pd.setMessage("Please wait...");
            pd.setTitle("Login");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            modifiedEmail = email.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            modifiedEmail = modifiedEmail.substring(0, modifiedEmail.length() - 3);
            // Storing user information in SharedPreferences after successful login
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id", modifiedEmail);
            editor.putInt("type", 2);
            editor.apply();

            DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Doctor");
            Query checkUserDatabase = dref.orderByChild("id").equalTo(modifiedEmail);

            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    pd.dismiss();
                    if (dataSnapshot.exists()) {
                        // User (doctor) with the provided username (email) exists in the database
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            // Retrieve the doctor's password from the database
                            String storedPassword = userSnapshot.child("pass").getValue(String.class);

                            // Check if the entered password matches the stored password
                            if (pass.equals(storedPassword)) {
                                // Save modifiedEmail in SharedPreferences
                                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("id", modifiedEmail);
                                editor.apply();

                                // Password is correct, proceed to MenuActivity for the doctor
                                Intent intent = new Intent(LoginActivity.this, MenuActivity2.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Incorrect password
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        // User (doctor) with the provided username (email) doesn't exist
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void userlog() {
        String email = String.valueOf( tieEmail.getText() );
        String pass = String.valueOf( tiePassword.getText() );
        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(LoginActivity.this, "Enter correct Email", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (pass.length()<6) {
            Toast.makeText(LoginActivity.this, "Password is of at least length 6", Toast.LENGTH_SHORT).show();
        } else{
            pd.setMessage("Please wait...");
            pd.setTitle("Login");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            modifiedEmail = email.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            modifiedEmail = modifiedEmail.substring(0, modifiedEmail.length() - 3);
            // Storing user information in SharedPreferences after successful login
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id", modifiedEmail);
            editor.putInt("type", 1);
            editor.apply();



            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pd.dismiss();

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");

                        databaseReference.child(modifiedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    }
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id", modifiedEmail);
                                    editor.apply();
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, ForceActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle error
                            }
                        });


                    }
                    else{
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void findAllViews(){

        tieEmail = findViewById(R.id.tieEmail);
        tiePassword = findViewById(R.id.tiePassword);
        tvSignUp = findViewById(R.id.tvSignup);
        tvForgot = findViewById(R.id.tvForgot);
        buttonLogin = findViewById(R.id.buttonLogin);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }


}
