package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientViewActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    CircleImageView profile;
    ImageView back;

    private TextView name, phone, type, age, blood, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        name = findViewById(R.id.tieName);
        phone = findViewById(R.id.phone);
        type = findViewById(R.id.type);
        age = findViewById(R.id.age);
        blood = findViewById(R.id.blood);
        gender = findViewById(R.id.gender);
        back = findViewById(R.id.backbtn);

        CardView viewPrescription = findViewById(R.id.vpres);
        CardView addPrescription = findViewById(R.id.apres);

        profile = findViewById(R.id.imageView4);

        // Get the doctor ID passed from the previous activity
        String patientID = getIntent().getStringExtra("PatientID");

        // Firebase reference to doctors node
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient").child(patientID);

        // Fetch data for the specific doctor
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve doctor information
                    String name2 = dataSnapshot.child("name").getValue().toString();
                    int age2 = Integer.parseInt(dataSnapshot.child("age").getValue().toString());
                    String gender2 = dataSnapshot.child("gender").getValue().toString();
                    String type2 = dataSnapshot.child("type").getValue().toString();
                    String phone2 = dataSnapshot.child("phone").getValue().toString();
                    String blood2 = dataSnapshot.child("bt").getValue().toString();
                    String imageUrl = dataSnapshot.child("imgurl").getValue().toString();

                    // Set the fetched data to TextViews
                    name.setText(name2);
                    type.setText("Prev Surgery: " + type2);
                    age.setText("Age: " + age2);
                    blood.setText("Blood: " + blood2);
                    gender.setText("Gender: " + gender2);
                    phone.setText("Phone: " + phone2);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Use your logic to load the image using Glide
                        Glide.with(PatientViewActivity.this)
                                .load(imageUrl)
                                .into(profile);
                    } else {
                        // Use a placeholder image if the URL is empty
                        profile.setImageResource(R.drawable.editprofile);
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors when fetching data
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the current activity
            }
        });

        // Set OnClickListener for viewPrescription
        viewPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientViewActivity.this, ViewPresActivity.class);
                intent.putExtra("PatientID", patientID);
                startActivity(intent);
            }
        });

        // Set OnClickListener for addPrescription
        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientViewActivity.this, AddPresActivity.class);
                intent.putExtra("PatientID", patientID);
                startActivity(intent);
            }
        });
    }
}