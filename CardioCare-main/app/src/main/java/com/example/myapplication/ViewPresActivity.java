package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewPresActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView txt;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pres);

        String patientID = getIntent().getStringExtra("PatientID");

        txt = findViewById(R.id.notepadTextView);
        back = findViewById(R.id.button);

        // Assuming prescription data is stored under "prescription" node for each patient
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("patient")
                .child(patientID)
                .child("pres");

        // Read prescription data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String prescription = dataSnapshot.getValue(String.class);
                    if (prescription != null) {
                        txt.setText(prescription);
                        if(prescription.equals("")) {
                            txt.setText("No prescription available");
                        }
                    }
                    else {
                        txt.setText("No prescription available");
                    }
                } else {
                    txt.setText("No prescription data found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity
            }
        });

    }
}