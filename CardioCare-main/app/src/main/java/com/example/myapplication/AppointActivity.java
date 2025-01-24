package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class AppointActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    app_adapter adapter;
    List<app_item> items = new ArrayList<app_item>();
    Button btn;
    DatabaseReference databaseReference;
    String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

        patientID = getIntent().getStringExtra("PatientID");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        btn = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new app_adapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("appoint");

        fetchDataFromFirebase();

        adapter.setOnDeleteItemClickListener(new app_adapter.OnDeleteItemClickListener() {
            @Override
            public void onDeleteItemClick(final int position) {
                // Show delete confirmation dialog
                showDeleteConfirmationDialog(position);
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

    private void fetchDataFromFirebase() {
        databaseReference.orderByChild("patient").equalTo(patientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear(); // Clear existing items

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve data and add it to the list
                    String name = snapshot.child("name").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);

                    items.add(new app_item(name, date, id));
                }

                // Sort the items list by date
                Collections.sort(items, new Comparator<app_item>() {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Assuming your date format

                    @Override
                    public int compare(app_item item1, app_item item2) {
                        try {
                            Date date1 = dateFormat.parse(item1.getDate());
                            Date date2 = dateFormat.parse(item2.getDate());
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });

                adapter.notifyDataSetChanged(); // Notify adapter of data change
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                progressDialog.dismiss();
            }
        });
    }


    private void deleteItem(int position) {
        // Get the selected item
        app_item selectedItem = items.get(position);

        // Remove item from RecyclerView
        items.remove(position);
        adapter.notifyItemRemoved(position);

        // Remove item from Firebase Database using the selected item's unique identifier (assuming you have such an identifier)
        databaseReference.child(selectedItem.getId()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Item successfully deleted from the database
                        Toast.makeText(AppointActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete item
                        Toast.makeText(AppointActivity.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                        // If deletion from Firebase fails, add the item back to the list
                        items.add(position, selectedItem);
                        adapter.notifyItemInserted(position);
                    }
                });
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call the deleteItem function when confirmation is positive
                        deleteItem(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog if canceled
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}