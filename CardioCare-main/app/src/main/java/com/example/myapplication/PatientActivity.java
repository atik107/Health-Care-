package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<patientlist> dataList;
    ProgressDialog progressDialog;
    patientadapter adapter;
    DatabaseReference databaseReference;
    SearchView searchView;
    patientlist androidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Set your desired message here
        progressDialog.setCancelable(false); // Set if the dialog is cancelable or not
        progressDialog.show();

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new patientadapter(this,dataList);
        recyclerView.setAdapter(adapter);
//        androidData = new patientlist("Asifur Rahman", "Normal", "A+ve", "1", "");
//        dataList.add(androidData);
//        androidData = new patientlist("Arifur Rahman", "Patient", "B-ve", "2", "");
//        dataList.add(androidData);
//        androidData = new patientlist("Atikul Islam", "Serious", "O+ve", "3", "");
//        dataList.add(androidData);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataList.clear(); // Clear existing data before adding new data

                    // Iterate through the data snapshot to retrieve doctor information
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Assuming your doctorlist class has appropriate constructor and getter methods
                        patientlist patient = snapshot.getValue(patientlist.class);
                        dataList.add(patient); // Add the retrieved doctor information to your list
                    }

                    adapter.notifyDataSetChanged(); // Notify adapter of data changes
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors when fetching data
                Toast.makeText(PatientActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void filterText(String text) {
        ArrayList<patientlist> filteredList = new ArrayList<>();

        for (patientlist patient : dataList) {
            // Here, you can define your filtering logic
            // For instance, if you want to filter by patient name
            if (patient.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(patient);
            }
        }

        if (filteredList.isEmpty()) {
            adapter.setSearchList(new ArrayList<>());
//            Toast.makeText(this, "No matching results found", Toast.LENGTH_SHORT).show();
        }
        else{
            // Pass the filtered list to the adapter
            adapter.setSearchList(filteredList);
        }
    }

}