package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView name,name2,blood,age,type,gender,phone,add;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    CardView c1,c2,c3;
    CircleImageView profile,pro2;
    ImageView img;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.tieName);
        blood = findViewById(R.id.blood);
        age = findViewById(R.id.age);
        type = findViewById(R.id.type);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        add = findViewById(R.id.address);
        profile = findViewById(R.id.imageView4);

        c1 = findViewById(R.id.bmi);
        c2 = findViewById(R.id.ow);
        c3 = findViewById(R.id.hi);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = preferences.getString("id", "null");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.pro_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_pro:
                        Toast.makeText(ProfileActivity.this,"ALready in Profile",Toast.LENGTH_SHORT);
                        return true;
                    case R.id.nav_pres:
                        intent = new Intent(ProfileActivity.this, ViewPresActivity.class);
                        intent.putExtra("PatientID", id);
                        startActivity(intent);
                        return true;
                    case R.id.nav_app:
                        intent = new Intent(ProfileActivity.this, AppointActivity.class);
                        intent.putExtra("PatientID", id);
                        startActivity(intent);
                        return true;
                    case R.id.nav_ep:
                        intent = new Intent(ProfileActivity.this, EditActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_eps:
                        intent = new Intent(ProfileActivity.this, ForgotActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_lo:
                        // Clear the stored preference (log out)
                        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("id"); // Remove the stored email or identifier
                        editor.apply();

                        // Proceed to the LoginActivity
                        intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return true;

                }
                return true;
            }
        });
        toolbar = findViewById(R.id.toolbar2);
        img = findViewById(R.id.imageView3);
        mAuth = FirebaseAuth.getInstance();

        View headerView = navigationView.getHeaderView(0);
        name2 = headerView.findViewById(R.id.sideName);
        pro2 = headerView.findViewById(R.id.propic);

//        name.setText(id);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");
        // Retrieve user data from Firebase
        progressDialog.show();
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    patient p = dataSnapshot.getValue(patient.class); // Create a User class that matches your database structure
//                    progressDialog.dismiss();
                    // Update UI with user data
                    name.setText(String.valueOf(p.getName()));
                    name2.setText(String.valueOf(p.getName()));
                    blood.setText("Blood: " + String.valueOf(p.getBt()));
                    age.setText("Age: " + String.valueOf(p.getAge()));
                    type.setText("Prev Surgery: " + String.valueOf(p.getType()));
                    gender.setText("Gender: " + String.valueOf(p.getGender()));
                    phone.setText("Phone: " + String.valueOf(p.getPhone()));
                    add.setText("Address: " + String.valueOf(p.getAddress()));

                    // Load image using Glide
                    String imageUrl = p.getImgurl();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Load image into ImageView using Glide
                        Glide.with(ProfileActivity.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.editprofile)
                                .into(profile);
                        Glide.with(ProfileActivity.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.editprofile)
                                .into(pro2);
                    } else {
                        // Use a placeholder image if no image URL is available
                        profile.setImageResource(R.drawable.editprofile);
                    }
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, BMIActivity.class);
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, OptimalWeightActivity.class);
                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HealthInfoActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}