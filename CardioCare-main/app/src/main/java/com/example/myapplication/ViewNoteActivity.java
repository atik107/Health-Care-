package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewNoteActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText txt;
    Button edit,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        txt = findViewById(R.id.notepadTextView);
        edit = findViewById(R.id.editButton);
        back = findViewById(R.id.backButton);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = preferences.getString("id", "null");

//        txt.setText(id);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Doctor")
                .child(id)
                .child("note");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String note = dataSnapshot.getValue(String.class);
                    txt.setText(note);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNote = txt.getText().toString();

                // Update the note in the database
                databaseReference.setValue(newNote)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ViewNoteActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                                // Update the UI with the edited note
                                txt.setText(newNote);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewNoteActivity.this, "Failed to update note", Toast.LENGTH_SHORT).show();
                            }
                        });
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