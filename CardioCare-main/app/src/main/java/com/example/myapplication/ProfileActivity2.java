package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity2 extends AppCompatActivity {

    public static DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView name,name2,exp,age,gender,phone,visit,room,time,edu,tag,day;
    CircleImageView profile,pro2;
    Toolbar toolbar;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        name = findViewById(R.id.tieName);
        exp = findViewById(R.id.exp);
        age = findViewById(R.id.age);
        visit = findViewById(R.id.visit);
        gender = findViewById(R.id.gender);
        phone = findViewById(R.id.phone);
        room = findViewById(R.id.room);
        day = findViewById(R.id.day);
        time = findViewById(R.id.time);
        edu = findViewById(R.id.edu);

        profile = findViewById(R.id.imageView4);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.pro_view);
        ImageView img = findViewById(R.id.imageView3);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_pro:
                        Toast.makeText(ProfileActivity2.this,"ALready in Profile",Toast.LENGTH_SHORT);
                        return true;
                    case R.id.nav_note:
                        intent = new Intent(ProfileActivity2.this, ViewNoteActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_ep:
                        Toast.makeText(ProfileActivity2.this,"Edit is Pressed",Toast.LENGTH_SHORT);
                        intent = new Intent(ProfileActivity2.this, EditActivity2.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_lo:
                        // Clear the stored preference (log out)
                        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("id"); // Remove the stored email or identifier
                        editor.apply();

                        // Proceed to the LoginActivity
                        intent = new Intent(ProfileActivity2.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return true;

                }
                return true;
            }
        });
        View headerView = navigationView.getHeaderView(0);
        name2 = headerView.findViewById(R.id.sideName);
        pro2 = headerView.findViewById(R.id.propic);
        tag = headerView.findViewById(R.id.sideTag);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = preferences.getString("id", "null");
//        name.setText(id);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor");
        // Retrieve user data from Firebase
        progressDialog.show();
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    doctor p = dataSnapshot.getValue(doctor.class); // Create a User class that matches your database structure

                    // Update UI with user data
                    name.setText(String.valueOf(p.getName()));
                    name2.setText(String.valueOf(p.getName()));
                    tag.setText("Doctor");
                    exp.setText("Exp: " + String.valueOf(p.getExp()) + " Years");
                    age.setText("Age: " + String.valueOf(p.getAge()));
                    visit.setText("Visit: " + String.valueOf(p.getVisit()) + " TK");
                    gender.setText("Gender: " + String.valueOf(p.getGender()));
                    phone.setText("Phone: " + String.valueOf(p.getPhone()));
                    room.setText("Room: " + String.valueOf(p.getRoom()));
                    day.setText("Day: " + String.valueOf(p.getDay()));
                    time.setText("Time: " + String.valueOf(p.getTime()));
                    edu.setText(String.valueOf(p.getEdu()));
//                    profile.setImageResource(R.drawable.bgmenu);
                    // Load image using Glide
                    String imageUrl = p.getImgurl();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Use your logic to load the image using Glide
                        Glide.with(ProfileActivity2.this)
                                .load(imageUrl)
                                .into(profile);
                        Glide.with(ProfileActivity2.this)
                                .load(imageUrl)
                                .into(pro2);
                    } else {
                        // Use a placeholder image if the URL is empty
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


    }
}