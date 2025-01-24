package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DoctorsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Spinner spinner;
    List<doctorlist> dataList,oglist;
    ProgressDialog progressDialog;
    doctoradapter adapter;
    SearchView searchView;
    DatabaseReference databaseReference;
    doctorlist androidData;
    String item;
    int x=0;
//    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        spinner = findViewById(R.id.spinnerSortBy);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctor");
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(DoctorsActivity.this, ""+item, Toast.LENGTH_SHORT).show();

                sortDoctorList(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new doctoradapter(this,dataList);
        recyclerView.setAdapter(adapter);
//        androidData = new doctorlist("Asifur Rahman", "Cardiologist", "5 years", "1", "1", 1500);
//        dataList.add(androidData);
//        androidData = new doctorlist("Zihad Rahman", "Cardiologist", "5 years", "2", "1", 5000);
//        dataList.add(androidData);
//        androidData = new doctorlist("Ratul Amin", "Dickologist", "5 years", "3", "1", 100);
//        dataList.add(androidData);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    dataList.clear(); // Clear existing data before adding new data

                    // Iterate through the data snapshot to retrieve doctor information
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Assuming your doctorlist class has appropriate constructor and getter methods
                        doctorlist doctor = snapshot.getValue(doctorlist.class);
                        dataList.add(doctor); // Add the retrieved doctor information to your list
                    }

                    adapter.notifyDataSetChanged(); // Notify adapter of data changes
                    progressDialog.dismiss();
                }
                oglist = new ArrayList<>(dataList);
                x = 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors when fetching data
                Toast.makeText(DoctorsActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Default");
        arrayList.add("Fees(Low to High)");
        arrayList.add("Fees(High to Low)");
        arrayList.add("Name(A to Z)");
        arrayList.add("Name(Z to A)");
        arrayList.add("Expertise");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }

    private void filterText(String text) {
        ArrayList<doctorlist> filteredList = new ArrayList<>();

        for (doctorlist doctor : dataList) {
            // Here, you can define your filtering logic
            // For instance, if you want to filter by patient name
            if (doctor.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(doctor);
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

    private void sortDoctorList(String sortOption) {
        switch (sortOption) {
            case "Fees(Low to High)":
                // Sort dataList based on fees in ascending order
                Collections.sort(dataList, new Comparator<doctorlist>() {
                    @Override
                    public int compare(doctorlist doctor1, doctorlist doctor2) {
                        return Integer.compare(doctor1.getVisit(), doctor2.getVisit());
                    }
                });
                break;
            case "Fees(High to Low)":
                // Sort dataList based on fees in descending order
                Collections.sort(dataList, new Comparator<doctorlist>() {
                    @Override
                    public int compare(doctorlist doctor1, doctorlist doctor2) {
                        return Integer.compare(doctor2.getVisit(), doctor1.getVisit());
                    }
                });
                break;
            case "Name(A to Z)":
                // Sort dataList based on name in ascending order
                Collections.sort(dataList, new Comparator<doctorlist>() {
                    @Override
                    public int compare(doctorlist doctor1, doctorlist doctor2) {
                        return doctor1.getName().compareToIgnoreCase(doctor2.getName());
                    }
                });
                break;
            case "Name(Z to A)":
                // Sort dataList based on name in ascending order
                Collections.sort(dataList, new Comparator<doctorlist>() {
                    @Override
                    public int compare(doctorlist doctor1, doctorlist doctor2) {
                        return doctor2.getName().compareToIgnoreCase(doctor1.getName());
                    }
                });
                break;
            case "Expertise":
                // Sort dataList based on expertise in ascending order
                Collections.sort(dataList, new Comparator<doctorlist>() {
                    @Override
                    public int compare(doctorlist doctor1, doctorlist doctor2) {
                        // Reverse the order of comparison to achieve descending order
                        return doctor1.getSpc().compareToIgnoreCase(doctor2.getSpc());
                    }
                });
                break;
            case "Default":
                // Load original unsorted data
                if(x==1){
                    loadOriginalData();
                    break;
                }

        }

        // Update RecyclerView with the sorted list
        adapter.setSearchList(dataList);
    }

    private void loadOriginalData() {
        // Set the dataList back to its original unsorted state
        dataList.clear();
        dataList.addAll(oglist);
        adapter.setSearchList(dataList);
    }
}