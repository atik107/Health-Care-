package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPresActivity extends AppCompatActivity {

    DatabaseReference databaseReference,dref,dref2;
    TextView t1,t2,t3;
    Button add,back;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pres);

        String patientID = getIntent().getStringExtra("PatientID");

        t1 = findViewById(R.id.date);
        t2 = findViewById(R.id.pres);
        t3 = findViewById(R.id.doc);
        add = findViewById(R.id.addButton);
        back = findViewById(R.id.backButton);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = preferences.getString("id", "null");

        dref = FirebaseDatabase.getInstance().getReference().child("Doctor").child(id);

        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    doctor p = dataSnapshot.getValue(doctor.class); // Create a User class that matches your database structure

                    // Update UI with user data
                    t3.setText("Dr. " + String.valueOf(p.getName()) + "\n" + String.valueOf(p.getSpc()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        // Get the current system date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        t1.setText(currentDate);

        // Assuming prescription data is stored under "prescription" node for each patient
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("patient")
                .child(patientID)
                .child("pres");
        dref2 = FirebaseDatabase.getInstance().getReference()
                .child("patient")
                .child(patientID)
                .child("email");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = t1.getText().toString();
                String prescription = t2.getText().toString();
                String doctor = t3.getText().toString();

                dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        email = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle potential errors
                    }
                });
                // Read existing prescription data from Firebase
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String existingPrescription = dataSnapshot.getValue(String.class);

                        // Append new prescription data to existing data
                        String newPrescription;
                        if (existingPrescription != null && !existingPrescription.isEmpty()) {
                            newPrescription = "Date: " + date + ":\n" + prescription + "\n\n" + "(By " + doctor + ")\n -------------------------------------------------------------- \n\n" + existingPrescription;
                        } else {
                            newPrescription = "Date: " + date + ":\n" + prescription + "\n\n" + "(By " + doctor + ")\n";
                        }

                        // Update the prescription data in Firebase
                        databaseReference.setValue(newPrescription)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Data successfully updated
                                        Toast.makeText(AddPresActivity.this, "Prescription added successfully", Toast.LENGTH_SHORT).show();
                                        String[] recipients = { email };
                                        String subject = "Prescription Added";
                                        String messege = "Your prescription has been added. Please check- \n\n" + "Date: " + date + ":\n" + prescription + "\n" + "(By " + doctor + ")\n";

                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                        intent.putExtra(Intent.EXTRA_TEXT, messege);

                                        intent.setType("message/rfc822");
                                        startActivity(Intent.createChooser(intent,"Choose an email client"));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure
                                        Toast.makeText(AddPresActivity.this, "Failed to add prescription", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle potential errors
                    }
                });
            }
        });

        // Handle back button click to finish the activity or navigate back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity
            }
        });

    }
}