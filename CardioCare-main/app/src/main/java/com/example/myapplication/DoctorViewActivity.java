package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    CircleImageView profile;
    ImageView call;
    ImageView back;
    String phone2;

    // TextViews to display doctor information
    private TextView name, phone, exp, age, visit, gender, room, time, about,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view);

        name = findViewById(R.id.tieName);
        phone = findViewById(R.id.phnno);
        exp = findViewById(R.id.exp);
        age = findViewById(R.id.age);
        visit = findViewById(R.id.visit);
        gender = findViewById(R.id.gender);
        room = findViewById(R.id.room);
        day = findViewById(R.id.day);
        time = findViewById(R.id.time);
        about = findViewById(R.id.about);
        back = findViewById(R.id.backbtn);
        call = findViewById(R.id.callbtn);

        profile = findViewById(R.id.imageView4);

        // Get the doctor ID passed from the previous activity
        String doctorID = getIntent().getStringExtra("DoctorID");

        // Firebase reference to doctors node
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor").child(doctorID);

        // Fetch data for the specific doctor
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve doctor information
                    String name2 = dataSnapshot.child("name").getValue().toString();
                    int age2 = Integer.parseInt(dataSnapshot.child("age").getValue().toString());
                    String gender2 = dataSnapshot.child("gender").getValue().toString();
                    String exp2 = dataSnapshot.child("exp").getValue().toString();
                    String room2 = dataSnapshot.child("room").getValue().toString();
                    String day2 = dataSnapshot.child("day").getValue().toString();
                    String time2 = dataSnapshot.child("time").getValue().toString();
                    phone2 = dataSnapshot.child("phone").getValue().toString();
                    int visit2 = Integer.parseInt(dataSnapshot.child("visit").getValue().toString());
                    String imageUrl = dataSnapshot.child("imgurl").getValue().toString();

                    // Set the fetched data to TextViews
                    name.setText(name2);
                    about.setText("About " + name2 + ":");
                    exp.setText("Exp: " + exp2 + " Years");
                    age.setText("Age: " + age2);
                    visit.setText("Visit: " + visit2 + " TK");
                    gender.setText("Gender: " + gender2);
                    phone.setText("Phone: " + phone2);
                    room.setText("Room: " + room2);
                    day.setText("Day: " + day2);
                    time.setText("Time: " + time2);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Use your logic to load the image using Glide
                        Glide.with(DoctorViewActivity.this)
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

        call.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Action when pressed
                        call.setImageResource(R.drawable.call2);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Action when released
                        call.setImageResource(R.drawable.call);
                        break;
                }
                return false;

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the phone number is available
                if (phone2 != null && !phone2.isEmpty()) {
                    // Create an intent to make a phone call
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone2));
                    startActivity(intent);
                } else {
                    // Handle the case where the phone number is not available
                    Toast.makeText(DoctorViewActivity.this, "Phone number not available", Toast.LENGTH_SHORT).show();
                }
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