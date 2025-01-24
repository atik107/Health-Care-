package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AppointActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView edit,count;
    app_adapter2 adapter;
    List<app_item2> items = new ArrayList<app_item2>();
    Button btn;
    DatabaseReference databaseReference;
    String docid;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint2);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        docid = preferences.getString("id", "null");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String systemDate = sdf.format(new Date());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        btn = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        edit = findViewById(R.id.pedit);
        edit.setText("Patient(" + systemDate + ")");
        count = findViewById(R.id.count);
        adapter = new app_adapter2(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("appoint");

        databaseReference.orderByChild("date").equalTo(systemDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear(); // Clear existing items

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String doctorID = snapshot.child("doctor").getValue(String.class);
                    String pname = snapshot.child("pname").getValue(String.class);
                    String gender = snapshot.child("gender").getValue(String.class);

                    // Check if doctor ID matches the logged-in doctor's ID
                    if (doctorID != null && doctorID.equals(docid)) {
                        // If both doctor ID and date match, add the item to the list
                        items.add(new app_item2(pname, gender));
                        x++;
                    }
                }

                adapter.notifyDataSetChanged(); // Notify adapter of data change
                progressDialog.dismiss();
                count.setText("Total Patient Today: " + x);
                x = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                progressDialog.dismiss();
            }
        });

        adapter.notifyDataSetChanged();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}