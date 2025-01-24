package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity2 extends AppCompatActivity {

    TextView edu,exp,gender,age;
    Button updateButton;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);

        edu = findViewById(R.id.edu);
        exp = findViewById(R.id.exp);
        gender = findViewById(R.id.editgender);
        age = findViewById(R.id.editage);
        updateButton = findViewById(R.id.updateButton);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = preferences.getString("id", "null");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor").child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    doctor p = dataSnapshot.getValue(doctor.class); // Create a User class that matches your database structure
                    // Update UI with user data
                    edu.setText(String.valueOf(p.getEdu()));
                    exp.setText(String.valueOf(p.getExp()));
                    gender.setText(String.valueOf(p.getGender()));
                    age.setText(String.valueOf(p.getAge()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        // Update the user profile in Firebase
        databaseReference.child("edu").setValue(edu.getText().toString());
        databaseReference.child("gender").setValue(gender.getText().toString());
        databaseReference.child("exp").setValue(exp.getText().toString());
        databaseReference.child("age").setValue(Integer.parseInt(age.getText().toString()));

        Toast.makeText(EditActivity2.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity or navigate back to the profile page
        Intent intent = new Intent(EditActivity2.this, ProfileActivity2.class);
        startActivity(intent);
        finish();
    }
}